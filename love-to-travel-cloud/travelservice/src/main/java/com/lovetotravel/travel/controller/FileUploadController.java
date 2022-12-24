package com.lovetotravel.travel.controller;


import com.lovetotravel.travel.entity.vo.PicUploadResult;
import com.lovetotravel.travel.result.Result;
import com.lovetotravel.travel.service.FileUploadService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传")
@RestController
@RequestMapping("/file")
@Slf4j
public class FileUploadController {

    final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    @ResponseBody
    public Result<PicUploadResult> upload(@RequestParam("file") MultipartFile multipartFile) {
        return Result.success(fileUploadService.upload(multipartFile));
    }


}
