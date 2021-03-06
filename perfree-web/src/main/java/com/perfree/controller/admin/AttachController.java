package com.perfree.controller.admin;

import cn.hutool.core.io.FileTypeUtil;
import com.perfree.common.FileUtil;
import com.perfree.common.Pager;
import com.perfree.common.ResponseBean;
import com.perfree.controller.BaseController;
import com.perfree.model.Attach;
import com.perfree.model.Tag;
import com.perfree.service.AttachService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 附件
 */
@Controller
@RequestMapping("/admin")
public class AttachController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(AttachController.class);
    @Value("${web.upload-path}")
    private String uploadPath;

    @Autowired
    private AttachService attachService;

    /**
     * 附件-图片选择页
     * @return String
     */
    @RequestMapping("/attach/img")
    public String index() {
        return "admin/pages/attach/attach-img";
    }

    /**
     * 文件上传
     * @return String
     */
    @PostMapping("/attach/upload")
    @ResponseBody
    public ResponseBean upload(HttpServletRequest request) {
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multiFile = multipartRequest.getFile("file");
            if (multiFile == null){
                logger.error("文件不能为空!");
                return ResponseBean.fail("文件不能为空!", null);
            }
            String multiFileName = multiFile.getOriginalFilename();
            if (StringUtils.isBlank(multiFileName)){
                logger.error("文件名不能为空!");
                return ResponseBean.fail("文件名不能为空!", null);
            }
            String type = FileUtil.getFileType(FileTypeUtil.getType(multiFile.getInputStream()));
            String suffix = multiFileName.substring(multiFileName.indexOf("."));
            String path = FileUtil.uploadMultiFile(multiFile, uploadPath, "attach");
            Attach attach = new Attach();
            attach.setName(multiFileName);
            attach.setSuffix(suffix);
            attach.setPath(path);
            attach.setType(type);
            if (attachService.add(attach) > 0){
                return ResponseBean.success("上传成功", attach);
            } else {
                logger.error("上传失败: {}",attach.toString());
                return ResponseBean.fail("上传失败",null);
            }
        }catch (Exception e){
            logger.error("上传失败: {}", e.getMessage());
            return ResponseBean.fail("上传失败", e.getMessage());
        }
    }

    /**
     * 附件管理列表数据
     * @return String
     */
    @PostMapping("/attach/list")
    @ResponseBody
    public Pager<Attach> list(@RequestBody Pager<Attach> pager) {
        return attachService.list(pager);
    }
}
