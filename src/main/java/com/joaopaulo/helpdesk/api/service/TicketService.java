package com.joaopaulo.helpdesk.api.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.joaopaulo.helpdesk.api.entity.ChangeStatus;
import com.joaopaulo.helpdesk.api.entity.Ticket;

@Component
public interface TicketService {

	Ticket createOrUpdate(Ticket ticket);

	Ticket findById(String id);

	void delete(String id);

	// paginação
	Page<Ticket> listTicket(int page, int count);

	// salva as alterações, quem fez, data
	ChangeStatus createChangeStatus(ChangeStatus changeStatus);

	Iterable<ChangeStatus> listChangeStatus(String ticketId);

	// pesquisa somente aquele ticket do usuário logado
	Page<Ticket> findByCurrentUser(int page, int count, String userId);

	// pesquisa por parâmetro
	Page<Ticket> findByParameters(int page, int count, String title, String status, String priority);

	// pesquisa por usuário
	Page<Ticket> findByParametersAndCurrentUser(int page, int count, String title, String status, String priority,
			String userId);

	// pesquisa pelo número do ticket
	Page<Ticket> findByNumber(int page, int count, Integer number);

	Iterable<Ticket> findAll(); // resumo de todos os tinckets

	// pesquisa somente aquele ticket do técnico logado
	Page<Ticket> findByParameterAndAssignerUser(int page, int count, String title, String status, String priority,
			String assignedUser);
}
