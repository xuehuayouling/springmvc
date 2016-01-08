package com.ysq.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysq.test.dao.AssetDAO;
import com.ysq.test.entity.Asset;
@Service
public class AssetService {
	@Autowired
	private AssetDAO assetDao;


	public AssetDAO getAssetDao() {
		return assetDao;
	}

	public void setAssetDao(AssetDAO assetDao) {
		this.assetDao = assetDao;
	}

	public List<Asset> getAssetList() {
		return assetDao.getAssetList();
	}
}
