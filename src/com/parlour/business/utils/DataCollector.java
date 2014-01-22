package com.parlour.business.utils;

import java.util.ArrayList;
import java.util.List;

import com.parlour.business.model.Category;
import com.parlour.business.model.Service;
import com.parlour.business.model.ServiceImage;
import com.parlour.business.presentation.wrapper.CategoryResultItem;
import com.parlour.business.presentation.wrapper.ResultItem;
import com.parlour.business.presentation.wrapper.ServiceResultItem;
import com.parlour.business.presentation.wrapper.SubCategoryResultItem;

public class DataCollector {
	
	private static List<ResultItem> categoryItemList = new ArrayList<ResultItem>();
	private static List<ResultItem> subCategoryServiceList = new ArrayList<ResultItem>();
	private static List<ResultItem> serviceList = new ArrayList<ResultItem>();
	private static List<ServiceImage> serviceImageList = new ArrayList<ServiceImage>();
	
	private static Category facial;
	private static Category hairRemoval;
	private static Category bodyTreatment;
	private static Category menipedi;
	private static Category makeup;
	private static Category specials;
	
	private static Category facialSubOne;
	private static Category facialSubTwo;
	private static Category facialSubThree;
	
	private static Service facialServiceOne;
	private static Service facialServiceTwo;
	private static Service facialServiceThree;
	
	private static ServiceImage serviceImageOne;
	private static ServiceImage serviceImageTwo;
	private static ServiceImage serviceImageThree;
	
	
	private static String serviceDesc = "We Specialize in Eyelash Extensions & offer you the best services available in Downtown Vancouver! Because we are committed to excellence, we use the highest grade products available today, assuring you beautiful, long-lasting eyelash extensions!";
	private static String serviceNote = "We offer Hair, Nail, Dr. Belter Facial Skin Care, Novalash Eyelash Extension and Waxing Hair Remove services at affordable pricing";
	
	static {
		
		facial = new Category(1, "Facial", "Its a Facial category", -1, -1);
		hairRemoval = new Category(2, "Hair Removal", "Its a Hair removal category", -1, -1);
		bodyTreatment = new Category(3, "Body Treatment", "Its a Body treatment category", -1, -1);
		menipedi = new Category(4, "Mani and Pedi", "Its a Mani and Pedi category", -1, -1);
		makeup = new Category(5, "Makeup", "Its a Makeup category", -1, -1);
		specials = new Category(6, "Specials", "Its a Specials category", -1, -1);
		
		//creating sub-category of facial category
		facialSubOne = new Category(101, "Facial sub-category one", "", 1, -1);
		facialSubTwo = new Category(102, "Facial sub-category two", "", 1, -1);
		facialSubThree = new Category(103, "Facial sub-category three", "", 1, -1);
		
		
		facialServiceOne = new Service(201, 1, "Facial service one", serviceDesc, 30.50, serviceNote, 1.5, -1);
		facialServiceTwo = new Service(202, 1, "Facial service two", serviceDesc, 30.50, serviceNote, 1, -1);
		facialServiceThree = new Service(203, 1, "Facial service three", serviceDesc, 30.50, serviceNote, 0.5, -1);
		
		serviceImageOne = new ServiceImage(501, "http://cdn7.staztic.com/app/a/2413/2413405/make-changeable-hairstyle-1-1-s-307x512.jpg");
		serviceImageTwo = new ServiceImage(502, "http://cdn8.staztic.com/app/a/2413/2413405/make-changeable-hairstyle-1-2-s-307x512.jpg");
		serviceImageThree = new ServiceImage(503, "http://cdn9.staztic.com/app/a/2413/2413405/make-changeable-hairstyle-1-0-s-307x512.jpg");
		
		categoryItemList.add(new CategoryResultItem(facial));
		categoryItemList.add(new CategoryResultItem(hairRemoval));
		categoryItemList.add(new CategoryResultItem(bodyTreatment));
		categoryItemList.add(new CategoryResultItem(menipedi));
		categoryItemList.add(new CategoryResultItem(makeup));
		categoryItemList.add(new CategoryResultItem(specials));
		
		subCategoryServiceList.add(new SubCategoryResultItem(facialSubOne));
		subCategoryServiceList.add(new SubCategoryResultItem(facialSubTwo));
		subCategoryServiceList.add(new SubCategoryResultItem(facialSubThree));
		
		subCategoryServiceList.add(new ServiceResultItem(facialServiceOne));
		subCategoryServiceList.add(new ServiceResultItem(facialServiceTwo));
		subCategoryServiceList.add(new ServiceResultItem(facialServiceThree));
		
		serviceList.add(new ServiceResultItem(facialServiceOne));
		serviceList.add(new ServiceResultItem(facialServiceTwo));
		serviceList.add(new ServiceResultItem(facialServiceThree));
		
		serviceImageList.add(serviceImageOne);
		serviceImageList.add(serviceImageTwo);
		serviceImageList.add(serviceImageThree);
	}
	
	public static List<ResultItem> getAllCategoryItems() {	
		return categoryItemList;
	}
	
	// now only returns facial sub-categories and services
	public static List<ResultItem> getAllSubCategoriesAndServices(int catId) {
		return subCategoryServiceList;
		
	}
	
	public static List<ResultItem> getAllServices(int catId) {
		return serviceList;
		
	}
	
	public static List<ServiceImage> getAllServiceImages(int serviceId){
		return serviceImageList;
	}

}
