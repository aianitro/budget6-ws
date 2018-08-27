package com.anpilog.budget.ws.utils;

import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.shared.dto.BalanceDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;

public class BalanceUtil {
	
	public static Double getBalanceAmount(BalanceDTO balanceDTO) {

		Double returnValue = 0.0;
		for (TotalDTO totalDTO : balanceDTO.getTotals())
			if(totalDTO.getStatus()==DataRetrievalStatus.COMPLETED)
				returnValue+=totalDTO.getAmount();
			else
				returnValue+=totalDTO.getPreviousAmount();
		
		return returnValue;
		
	}

}
