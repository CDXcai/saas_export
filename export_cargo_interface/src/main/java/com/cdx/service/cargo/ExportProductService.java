package com.cdx.service.cargo;


import com.cdx.domain.cargo.ExportProduct;
import com.cdx.domain.cargo.ExportProductExample;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface ExportProductService {

	//根据条件查询
	List<ExportProduct> findAll(ExportProductExample example);
}
