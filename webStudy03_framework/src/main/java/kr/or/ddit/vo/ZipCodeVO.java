package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of="zipcode")
public class ZipCodeVO implements Serializable{
	private String zipcode;
	private String sido;
	private String gugun;
	private String dong;
	private String ri;
	private String bldg;
	private String bunji;
	private Integer seq;
	
	public String getAddress() {
		StringBuffer address = new StringBuffer();
		address.append(sido);
		address.append(" " + gugun);
		if(dong!=null) address.append(" " + dong);
		if(ri!=null) address.append(" " + ri);
		if(bldg!=null) address.append(" " + bldg);
		if(bunji!=null) address.append(" " + bldg);
		if(seq!=null) address.append(" " + seq);
		return address.toString();
	}
}










