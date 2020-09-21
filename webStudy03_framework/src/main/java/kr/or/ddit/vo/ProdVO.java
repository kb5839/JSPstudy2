package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of= {"prod_id"})
public class ProdVO implements Serializable{
	@NotBlank
	@Size(max = 10)
	private String prod_id;
	@NotBlank
	@Size(max = 40)
	private String prod_name;
	@NotBlank
	@Size(max = 4)
	private String prod_lgu;
	@NotBlank
	@Size(max = 6)
	private String prod_buyer;
	@NotBlank
	private Integer prod_cost;
	@NotBlank
	private Integer prod_price;
	@NotBlank
	private Integer prod_sale;
	@NotBlank
	@Size(max = 100)
	private String prod_outline;
	@Size(max = 4000)
	private String prod_detail;
	@NotBlank
	@Size(max = 40)
	private String prod_img;
	@NotBlank
	private Integer prod_totalstock;
	@Size(max = 7)
	private String prod_insdate;
	@NotBlank
	private Integer prod_properstock;
	@Size(max = 20)
	private String prod_size;
	@Size(max = 20)
	private String prod_color;
	@Size(max = 255)
	private String prod_delivery;
	private String prod_unit;
	private Integer prod_qtyin;
	private Integer prod_qtysale;
	private Integer prod_mileage;
	
//	private BuyerVO buyer; // Prod has a Buyer 관계(1:1 관계 조인시 모델)
	
	private List<MemberVO> memberList; // 구매자 목록, Prod has many Member
}












