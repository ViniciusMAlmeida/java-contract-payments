package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, Integer months) {
		
		double basicQuota = contract.getTotalValue() / months;
		
		for (int i = 1; i <= months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i);
			Double interest = onlinePaymentService.interest(basicQuota, i);
			Double fee = onlinePaymentService.paymentFee(interest);
			Installment installment = new Installment(dueDate, fee);
			contract.getInstallments().add(installment);
		}
	}
}
