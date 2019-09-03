package com.cloud.platform.service.impl;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.dao.PlatformDataDao;
import com.cloud.platform.model.PlatformAadhaarDataModel;
import com.cloud.platform.service.PlatformDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 平台数据管理服务实现
 *
 * @author bjy
 * @date 2019/3/12 0012 14:22
 */
@Service
public class PlatformDataServiceImpl implements PlatformDataService {

    @Autowired
    private PlatformDataDao dataDao;

    /**
     * 获取内部Aadhaar数据
     *
     * @param params
     * @return
     */
    @Override
    public JsonResult getInnerAadhaar(Map<String, Object> params) {
        PageHelper.startPage(MapUtils.getIntValue(params, "page"), MapUtils.getIntValue(params, "limit"));
        List<PlatformAadhaarDataModel> list = dataDao.getInnerAadhaar(params);
        PageInfo<PlatformAadhaarDataModel> page = new PageInfo<PlatformAadhaarDataModel>(list);
        return JsonResult.ok(page.getList(), (int) page.getTotal());
    }

    /**
     * 获取外部Aadhaar数据
     *
     * @param params
     * @return
     */
    @Override
    public JsonResult getOuterAadhaar(Map<String, Object> params) {
        PageHelper.startPage(MapUtils.getIntValue(params, "page"), MapUtils.getIntValue(params, "limit"));
        List<PlatformAadhaarDataModel> list = dataDao.getOuterAadhaar(params);
        PageInfo<PlatformAadhaarDataModel> page = new PageInfo<PlatformAadhaarDataModel>(list);
        return JsonResult.ok(page.getList(), (int) page.getTotal());
    }

    /**
     * 上传数据到外部Aadhaar
     *
     * @param params
     * @return
     */
    @Override
    public JsonResult uploadOutAadhaar(Map<String, Object> params) throws IOException {
        String fileName = MapUtils.getString(params, "url");
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {

            return JsonResult.errorException("文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        InputStream is = null;
//        is = new FileInputStream(new File(fileName));
        is = new URL(fileName).openStream();

        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        List<PlatformAadhaarDataModel> list = new ArrayList<>();
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            String userName = row.getCell(0).getStringCellValue();
            String userAadhaarNo = row.getCell(1).getStringCellValue();
            String userMobile = row.getCell(2).getStringCellValue();
            String userAddress = row.getCell(3).getStringCellValue();
            String createTime = row.getCell(4).getStringCellValue();
            PlatformAadhaarDataModel model = new PlatformAadhaarDataModel();
            model.setUserName(userName);
            model.setUserMobile(userMobile);
            model.setUserAadhaarNo(userAadhaarNo);
            model.setUserAddress(userAddress);
            model.setCreateTime(Timestamp.valueOf(createTime));
            list.add(model);
        }
        int total = dataDao.insertOutAadhaarByList(list);
        if (total > 0) {
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:上传数据失败");
    }

    /**
     * 筛选重复Aadhaar
     *
     * @param params
     * @return
     */
    @Override
    public JsonResult screenOutAadhaar(Map<String, Object> params) {
        PageHelper.startPage(MapUtils.getIntValue(params, "page"), MapUtils.getIntValue(params, "limit"));
        List<PlatformAadhaarDataModel> list = dataDao.screenOutAadhaar(params);
        PageInfo<PlatformAadhaarDataModel> page = new PageInfo<PlatformAadhaarDataModel>(list);
        return JsonResult.ok(page.getList(), (int) page.getTotal());
    }

    /**
     * 删除重复Aadhaar
     *
     * @param params
     * @return
     */
    @Override
    public JsonResult deleteRepeatAadhaar(Map<String, Object> params) {
        int i = dataDao.deleteRepeatAadhaar(params);
        if (i > 0) {
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:删除重复Aadhaar失败！");
    }
}
