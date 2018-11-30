package com.model2.mvc.web.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;
                                                                                                                                                                                                                                   

//==> ȸ������ Controller
@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {
	

	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	//setter Method ���� ����
		
	public PurchaseRestController(){
		System.out.println(this.getClass());                                           
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	//�����ؾ��Ѵ�
	@RequestMapping(value="json/addPurchase/{prodNo}",method=RequestMethod.GET)
	public Product addPurchase(@PathVariable("prodNo") int prodNo) throws Exception {

		System.out.println("/purchase/json/addPurchase{prodNo} GET");
	
	
		Product product = productService.getProduct(prodNo);
		
	
		return product;
	}
	
	
	@RequestMapping(value="json/addPurchase",method=RequestMethod.POST)
	public Purchase addPurchase( @RequestBody Purchase purchase) throws Exception {

		System.out.println("json/addPurchase : POST");
		//Business Logic
		
		purchase.setTranCode("1");
		
		purchaseService.addPurchase(purchase);
		purchase=purchaseService.getPurchase2(purchase.getPurchaseProd().getProdNo());
		
		
		return purchase;
	}
	

	@RequestMapping(value = "json/getPurchase/{tranNo}", method = RequestMethod.GET)
	public Purchase getPurchase(@PathVariable("tranNo") int tranNo) throws Exception {
		
		System.out.println("/purchase/getPurchase get���!");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		
		
		return purchase;
	}


	@RequestMapping(value = "json/updatePurchase/{tranNo}", method = RequestMethod.GET)
	public Purchase updatePurchase( @PathVariable("tranNo") int tranNo ) throws Exception{

		System.out.println("json/updatePurchase/{tranNo} GET");
		//Business Logic
		Purchase purchase=purchaseService.getPurchase(tranNo);
		// Model �� View ����
		
		
		return purchase;
	}
	

	@RequestMapping(value = "json/updatePurchase", method = RequestMethod.POST)
	public Purchase updatePurchase( @RequestBody Purchase purchase ) throws Exception{

		System.out.println("/purchase/updatePurchase");
		//Business Logic
		
		purchaseService.updatePurcahse(purchase);
		Purchase purchase2=purchaseService.getPurchase(purchase.getTranNo());
		
		
		return purchase;
	}


	@RequestMapping(value="json/listPurchase", method = RequestMethod.POST)
	public Map<String, Object> listPurchase( @RequestBody Search search, HttpSession session) throws Exception{
		
		int currentPage=0;
		System.out.println("json/listPurchase");
		//String buyerId = ((User)session.getAttribute("user")).getUserId();
		if(search.getCurrentPage() ==0||currentPage ==0){
			search.setCurrentPage(1);
		}else {
			search.setCurrentPage(currentPage);	
		}
		
		search.setPageSize(pageSize);
		search.setOrder(search.getOrder());
		
		// Business logic ����
		Map<String , Object> map=purchaseService.getPurchaseList(search, "user11");
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		Map<String , Object> map1=new HashMap<String, Object>();
		
		map1.put("list", map.get("list"));
		map1.put("resultPage", resultPage);
		map1.put("search", search);
	
		
		return map1;
	}
	
	@RequestMapping(value="json/listPurchase", method = RequestMethod.GET)
	public Map<String, Object> listPurchase( ) throws Exception{
		
		int currentPage=0;
		Search search = new Search();
		search.setCurrentPage(1);
		search.setOrder(null);
		search.setPageSize(5);
		search.setSearchCondition("");
		search.setSearchKeyword("");
	
		System.out.println("json/listPurchase");
		//String buyerId = ((User)session.getAttribute("user")).getUserId();
		if(search.getCurrentPage() ==0||currentPage ==0){
			search.setCurrentPage(1);
		}else {
			search.setCurrentPage(currentPage);	
		}
		
		search.setPageSize(pageSize);
		search.setOrder(search.getOrder());
		
		// Business logic ����
		Map<String , Object> map=purchaseService.getPurchaseList(search, "user11");
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		Map<String , Object> map1=new HashMap<String, Object>();
		
		map1.put("list", map.get("list"));
		map1.put("resultPage", resultPage);
		map1.put("search", search);
	
		
		return map1;
	}

	@RequestMapping(value="json/updateTranCodeActionByProd/{prodNo}/{tranCode}",method=RequestMethod.GET)
	public Purchase updateTranCodeActionByProd(@PathVariable("prodNo") int prodNo,	@PathVariable("tranCode") String tranCode ) throws Exception{

		System.out.println("/purchase/json/updateTranCodeActionByProd/{prodNo}/{tranCode}");
		//Business Logic
		
		Purchase purchase=purchaseService.getPurchase2(prodNo);		
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);
		
		return purchase;
	}
	
	@RequestMapping(value="json/updateTranCode/{tranNo}/{tranCode}",method=RequestMethod.GET)
	public Purchase updateTranCode(@PathVariable("tranNo") int tranNo,	@PathVariable("tranCode") String tranCode,HttpSession session ) throws Exception{

		System.out.println("/purchase/updateTranCode");
		//Business Logic
		
		Purchase purchase=purchaseService.getPurchase(tranNo);		
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);
		
		return purchase;
	}
}