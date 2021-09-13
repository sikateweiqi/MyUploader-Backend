package cn.attackme.myuploader.service;

import cn.attackme.myuploader.config.UploadConfig;
import cn.attackme.myuploader.dao.FileDao;
import cn.attackme.myuploader.model.File;
import cn.attackme.myuploader.model.excltemplate.ExclTemplateDemo;
import cn.attackme.myuploader.model.excltemplate.SlrEmpSalary;
import cn.attackme.myuploader.utils.FileUtils;
import cn.attackme.myuploader.utils.excel.ExcelUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static cn.attackme.myuploader.utils.FileUtils.generateFileName;
import static cn.attackme.myuploader.utils.UploadUtils.*;

/**
 * 文件上传服务
 */
@Service
public class FileService {
    @Autowired
    private FileDao fileDao;


    /**
     * 上传文件
     * @param md5
     * @param file
     */
    public void upload(String md5,
                       MultipartFile file) throws IOException {
        String path = UploadConfig.path + generateFileName();
        String fileName = file.getOriginalFilename();
        FileUtils.write(path, file.getInputStream());
        try {
            List< SlrEmpSalary> empSalaryList =
                    (List< SlrEmpSalary>)ExcelUtils.parseExcelToList(file.getInputStream(),  SlrEmpSalary.class);            System.out.println(new Gson().toJson(empSalaryList));

//            OutputStream outputStream = null;
//            java.io.File f = new java.io.File("test.xlsx");
//            outputStream = new FileOutputStream(f);
//
//
//            //导出数据
//            List<SlrEmpSalary> dataList = new ArrayList<SlrEmpSalary>();
//            SlrEmpSalary model = new SlrEmpSalary();
//            model.setSeqNumber(1);
//            model.setEmployeeCode("a");
//            model.setEmployeeName("cao");
//            dataList.add(model);
//
//            Map<Integer,Map<String,String>> select = new HashMap<>();
//            //导出
//            ExcelUtils.exportExcel(outputStream, dataList,  SlrEmpSalary.class, null, "test");
//
//            System.out.println(outputStream.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        fileDao.save(new File(fileName, md5, path, new Date()));
    }

    /**
     * 分块上传文件
     * @param md5
     * @param size
     * @param chunks
     * @param chunk
     * @param file
     * @throws IOException
     */
    public void uploadWithBlock(String name,
                                String md5,
                                Long size,
                                Integer chunks,
                                Integer chunk,
                                MultipartFile file) throws IOException {
        String fileName = getFileName(md5, chunks);
        FileUtils.writeWithBlok(UploadConfig.path + fileName, size, file.getInputStream(), file.getSize(), chunks, chunk);
        addChunk(md5,chunk);
        if (isUploaded(md5)) {
            removeKey(md5);
            fileDao.save(new File(name, md5,UploadConfig.path + fileName, new Date()));
        }
    }

    /**
     * 检查Md5判断文件是否已上传
     * @param md5
     * @return
     */
    public boolean checkMd5(String md5) {
        File file = new File();
        file.setMd5(md5);
        return fileDao.getByFile(file) == null;
    }
}
