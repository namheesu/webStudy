package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="buyerId")
public class BuyerVO implements Serializable{
	private int rnum;
	@NotBlank(groups = {UpdateGroup.class, DeleteGroup.class})
	private String buyerId;
	@NotBlank
	private String buyerName;
	@NotBlank(groups = InsertGroup.class)
	private String buyerLgu;
	private String buyerBank;
	private String buyerBankno;
	private String buyerBankname;
	private String buyerZip;
	private String buyerAdd1;
	private String buyerAdd2;
	@NotBlank
	private String buyerComtel;
	@NotBlank
	private String buyerFax;
	@NotBlank
	@Email
	private String buyerMail;
	private String buyerCharger;
	private String buyerTelext;
	
	// Has A 관계 - 1:1 -> association
	private Map<String, Object> lprod;	 // Buyer와 Lprod
	
	// Has Many관계 - 1:N -> collection
	private List<ProdVO> prodList; 	// 제조사와의 거래품목	
}
