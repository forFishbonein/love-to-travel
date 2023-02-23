package com.lovetotravel.travel.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lovetotravel.travel.entity.AliPay;
import com.lovetotravel.travel.entity.vo.product.ProductBuy;
import com.lovetotravel.travel.mapper.ProductBuyMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "支付")
@RestController
@RequestMapping("/alipay")
public class AliPayController {

    final ProductBuyMapper productBuyMapper;

    public AliPayController(ProductBuyMapper productBuyMapper) {
        this.productBuyMapper = productBuyMapper;
    }

    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public String pay(AliPay aliPay) {
        AlipayTradePagePayResponse response;
        try {
            //  发起API调用（以创建当面付收款二维码为例）
            response = Factory.Payment.Page()
                    .pay(URLEncoder.encode("Love-To-Travel-Product", "UTF-8"), aliPay.getId(), String.valueOf(aliPay.getCost()), "");
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return response.getBody();
    }

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");


            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
//                 System.out.println(name + " = " + request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");
            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                System.out.println("买家付款时间: " + params.get("gmt_payment"));

                QueryWrapper<ProductBuy> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(ProductBuy::getId, tradeNo);
                ProductBuy productBuy = productBuyMapper.selectOne(queryWrapper);
                if (productBuy != null) {
                    productBuy.setPaystate(1);
                    System.out.println("123");
                    productBuy.setPayTime(gmtPayment);
                    productBuyMapper.update(productBuy, queryWrapper);
                }

            }
        }
        return "success";
    }
}

