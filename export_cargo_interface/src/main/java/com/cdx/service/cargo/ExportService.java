package com.cdx.service.cargo;

import com.cdx.domain.cargo.Export;
import com.cdx.domain.cargo.ExportExample;
import com.cdx.domain.vo.ExportResult;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface ExportService {

    Export findById(String id);

    void save(Export export);

    void update(Export export);

    void delete(String id);

	PageInfo findAll(ExportExample example, int page, int size);

	//更新报运结果
	void exportE(ExportResult result);

	// 更新报运单为已上报状态
    void exportR(Export export);


    // 查询指定状态的报运单数据
    List<Export> findByStatus(long i);
}
