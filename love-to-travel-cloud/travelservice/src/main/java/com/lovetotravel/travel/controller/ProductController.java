package com.lovetotravel.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lovetotravel.travel.entity.City;
import com.lovetotravel.travel.entity.Plan;
import com.lovetotravel.travel.entity.Product;
import com.lovetotravel.travel.entity.Province;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.vo.NewNum;
import com.lovetotravel.travel.entity.vo.product.ProductBuy;
import com.lovetotravel.travel.entity.vo.product.ProductSales;
import com.lovetotravel.travel.entity.vo.product.ProductShowVo;
import com.lovetotravel.travel.entity.vo.product.ProductVo;
import com.lovetotravel.travel.exception.GlobalException;
import com.lovetotravel.travel.mapper.ProductBuyMapper;
import com.lovetotravel.travel.mapper.ProductMapper;
import com.lovetotravel.travel.result.CodeMsg;
import com.lovetotravel.travel.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Api(tags = "旅游产品接口")
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    final ProductMapper productMapper;
    final ProductBuyMapper productBuyMapper;
    final MongoTemplate mongoTemplate;

    public ProductController(ProductMapper productMapper, ProductBuyMapper productBuyMapper, MongoTemplate mongoTemplate) {
        this.productMapper = productMapper;
        this.productBuyMapper = productBuyMapper;
        this.mongoTemplate = mongoTemplate;
    }


    @ApiOperation("根据id查询产品")
    @GetMapping("/{id}")
    public Result<ProductShowVo> getById(@PathVariable("id") String id) {
        Product product = productMapper.selectById(id);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(product.getPlanId()));
        Plan plan = mongoTemplate.findOne(query, Plan.class);
        ProductShowVo productShowVo = new ProductShowVo();
        BeanUtils.copyProperties(product, productShowVo);
        productShowVo.setPlan(plan);
        return Result.success(productShowVo);
    }

    @ApiOperation("查询所有产品")
    @GetMapping
    public Result<List<ProductShowVo>> getAll() {
        List<Product> productList = productMapper.selectList(null);

        List<ProductShowVo> productShowVoList = new ArrayList<>();

        if (productList.size() != 0) {
            for (Product p : productList) {
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(p.getPlanId()));
                Plan plan = mongoTemplate.findOne(query, Plan.class);
                ProductShowVo productShowVo = new ProductShowVo();
                BeanUtils.copyProperties(p, productShowVo);
                productShowVo.setPlan(plan);
                productShowVoList.add(productShowVo);
            }
        }
        System.out.println(productShowVoList);
        return Result.success(productShowVoList);
    }

    @ApiOperation("所有产品分页")
    @PostMapping("/page")
    public Result<Page<ProductShowVo>> getPage(@RequestBody PageVo pageVo) {
        Page<Product> page = Page.of(pageVo.getPageNum(), pageVo.getPageSize());
        page.addOrder(OrderItem.desc("create_time"));
        Page<Product> productPage = productMapper.selectPage(page, null);

        List<ProductShowVo> productShowVoList = new ArrayList<>();

        //获取plan
        if (productPage.getRecords().size() != 0) {
            for (Product p : productPage.getRecords()) {
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(p.getPlanId()));
                Plan plan = mongoTemplate.findOne(query, Plan.class);
                ProductShowVo productShowVo = new ProductShowVo();
                BeanUtils.copyProperties(p, productShowVo);
                productShowVo.setPlan(plan);
                productShowVoList.add(productShowVo);
            }
        }
        Page<ProductShowVo> productShowVoPage = new Page<>();
        BeanUtils.copyProperties(productPage, productShowVoPage);
        productShowVoPage.setRecords(productShowVoList);
        return Result.success(productShowVoPage);
    }

    @ApiOperation("根据用户id查询")
    @GetMapping("/user/{id}")
    public Result<List<ProductShowVo>> getByUserId(@PathVariable("id") String id) {

        List<ProductShowVo> buyInfo = productBuyMapper.getBuyInfo(id);
        for (ProductShowVo q : buyInfo) {
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(q.getPlanId()));
                Plan plan = mongoTemplate.findOne(query, Plan.class);
                q.setPlan(plan);
            }
        return Result.success(buyInfo);
    }

    @ApiOperation("获取所有订单")
    @PostMapping("/page/order")
    public Result<Page<ProductBuy>> getAllOrder(@RequestBody PageVo pageVo) {
        Page<ProductBuy> page = Page.of(pageVo.getPageNum(), pageVo.getPageSize());
        Page<ProductBuy> productBuyPage = productBuyMapper.selectPage(page, null);
        return Result.success(productBuyPage);
    }

    @ApiOperation("新增产品")
    @PostMapping
    public Result<String> insert(@RequestBody ProductVo productVo) {
        Product product = new Product();
        BeanUtils.copyProperties(productVo, product);
        System.out.println(product);
        productMapper.insert(product);
        return Result.success("新增成功");
    }

    @ApiOperation("使用产品")
    @PostMapping("/use/{id}")
    public Result<String> use(@PathVariable("id") String id) {
        QueryWrapper<ProductBuy> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ProductBuy::getId, id);
        ProductBuy productBuy = productBuyMapper.selectOne(queryWrapper);
        if (productBuy.getStatus().equals("0")) {
            productBuy.setStatus("1");
            productBuyMapper.update(productBuy,queryWrapper);
            return Result.success("使用成功");
        }
        return Result.success("使用失败");
    }

    @ApiOperation("购买产品")
    @PostMapping("/buy")
    public Result<String> buy(@RequestBody ProductBuy productBuy) {
        Product product = productMapper.selectById(productBuy.getProductId());
        if (product == null) {
            throw new GlobalException(CodeMsg.PRODUCT_NOT_EXIST);
        }

        Integer randomId = new Random().nextInt(999999);//生成随机数，最大为999999
        if (randomId < 100000) {
            randomId = randomId + 100000;//保证随机数为6位数字
        }
        QueryWrapper<ProductBuy> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ProductBuy::getId, randomId);
        ProductBuy productBuyInMysql = productBuyMapper.selectOne(queryWrapper);
        if (productBuyInMysql != null) {
            randomId = new Random().nextInt(999999);//生成随机数，最大为999999
            if (randomId < 100000) {
                randomId = randomId + 100000;//保证随机数为6位数字
            }
        }
        productBuy.setId(randomId.toString());
        productBuy.setStatus("0");
        productBuy.setPaystate(0);

        productBuyMapper.insert(productBuy);
        return Result.success(randomId.toString());
    }

    @GetMapping("/isbuy/{id}")
    public Result<Boolean> isBuy(@PathVariable("id") String id) {
        QueryWrapper<ProductBuy> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ProductBuy::getId, id).eq(ProductBuy::getPaystate, 1);
        ProductBuy productBuyInMysql = productBuyMapper.selectOne(queryWrapper);
        if (productBuyInMysql != null) {
            return Result.success(true);
        }
        return Result.success(false);
    }


    @ApiOperation("7天订单统计")
    @GetMapping("/new")
    public Result<List<NewNum>> getNewProductNum() {
        return Result.success(productBuyMapper.getNewProductNum());
    }

    @ApiOperation("获取订单总数")
    @GetMapping("/all")
    public Result<Integer> getAllProductBuy() {
        return Result.success(productBuyMapper.selectCount(null));
    }

    @ApiOperation("7天销售额统计")
    @GetMapping("/cost/new")
    public Result<List<NewNum>> getNewProductCost() {
        return Result.success(productBuyMapper.getNewProductBuyNum());
    }

    @ApiOperation("获取总销售额")
    @GetMapping("/cost/all")
    public Result<NewNum> getAllProductCost() {
        return Result.success(productBuyMapper.getCost());
    }

    @ApiOperation("获取下单量")
    @GetMapping("/orders")
    public Result<List<ProductSales>> getOrder() {
        List<ProductSales> sales = productBuyMapper.getOrder();

        for (ProductSales s: sales) {
            Product product = productMapper.selectById(s.getId());
            s.setName(product.getName());
        }
        return Result.success(sales);
    }

    @ApiOperation("获取销量")
    @GetMapping("/sales")
    public Result<List<ProductSales>> getSales() {
        List<ProductSales> sales = productBuyMapper.getSales();

        for (ProductSales s: sales) {
            Product product = productMapper.selectById(s.getId());
            s.setName(product.getName());
        }
        return Result.success(sales);
    }

    @ApiOperation("获取销量")
    @GetMapping("/use")
    public Result<List<ProductSales>> getUse() {
        List<ProductSales> sales = productBuyMapper.getUse();

        for (ProductSales s: sales) {
            Product product = productMapper.selectById(s.getId());
            s.setName(product.getName());
        }
        return Result.success(sales);
    }




}


