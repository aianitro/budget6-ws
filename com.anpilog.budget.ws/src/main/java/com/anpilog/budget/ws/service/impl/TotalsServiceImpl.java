package com.anpilog.budget.ws.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.anpilog.budget.ws.exceptions.CouldNotDeleteRecordException;
import com.anpilog.budget.ws.exceptions.CouldNotUpdateRecordException;
import com.anpilog.budget.ws.exceptions.NoRecordFoundException;
import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.service.TotalsService;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;
import com.anpilog.budget.ws.utils.DateUtils;
import com.anpilog.budget.ws.utils.TotalUtils;

public class TotalsServiceImpl implements TotalsService {

	DAO database;
	TotalUtils totalUtils = new TotalUtils();

	public TotalsServiceImpl(DAO database) {
		this.database = database;
	}

	@Override
	public List<TotalDTO> getTotals() {

		List<TotalDTO> totals = null;

		try {
			this.database.openConnection();
			totals = this.database.getTotals();
		} finally {
			this.database.closeConnection();
		}

		return totals;
	}

	@Override
	public List<TotalDTO> getLastTotals() {

		List<TotalDTO> totals = null;

		try {
			this.database.openConnection();
			totals = this.database.getLastTotals();
		} finally {
			this.database.closeConnection();
		}

		return totals;
	}

	@Override
	public List<TotalDTO> getOutdatedTotals() {

		return getLastTotals().stream()
				.filter(t -> !DateUtils.isDateToday(t.getDate()) && t.getAccount().getIsAutomated())
				.collect(Collectors.toList());

	}
	
	public List<TotalDTO> prepareNewTotals(){
		
		List<TotalDTO> newTotals = new ArrayList<TotalDTO>();
		for (TotalDTO totalDto : getOutdatedTotals()) {
			TotalDTO newTotalDto = new TotalDTO();
			newTotalDto.setAccount(totalDto.getAccount());
			newTotalDto.setPreviousAmount(totalDto.getAmount());
			newTotalDto.setDate(LocalDate.now());
			newTotalDto.setStatus(DataRetrievalStatus.PENDING);
			newTotals.add(newTotalDto);
		}
		
		return newTotals;
	}

	@Override
	public TotalDTO getTotal(String id) {
		TotalDTO returnValue = null;

		try {
			this.database.openConnection();
			returnValue = this.database.getTotal(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}

	@Override
	public TotalDTO createTotal(TotalDTO totalDto) {

		// Record data into a database
		TotalDTO returnValue = this.saveTotal(totalDto);

		// Return back the bank
		return returnValue;
	}

	@Override
	public void updateTotal(TotalDTO totalDto) {
		try {
			this.database.openConnection();
			this.database.updateTotal(totalDto);
		} catch (Exception ex) {
			throw new CouldNotUpdateRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

	private TotalDTO saveTotal(TotalDTO totalDto) {

		TotalDTO returnValue = null;

		try {
			this.database.openConnection();
			returnValue = this.database.saveTotal(totalDto);
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}

	@Override
	public void deleteTotal(TotalDTO totalDto) {

		try {
			this.database.openConnection();
			this.database.deleteTotal(totalDto);
		} catch (Exception ex) {
			throw new CouldNotDeleteRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

}
