package com.hedera.hedera.entitiy;

import lombok.Getter;
import lombok.Setter;

public class PaymentCard extends Payment {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String number;

	@Getter
	@Setter
	private String cardHolder;

	@Getter
	@Setter
	private String expirationMonth;

	@Getter
	@Setter
	private String expirationYear;

	@Getter
	@Setter
	private String securityCode;

	@Getter
	@Setter
	private String cardBrand;

}
