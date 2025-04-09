package com.example.backend.service;

import com.example.backend.dto.TicketDTO;
import com.example.backend.dto.TicketResponseDTO;
import com.example.backend.entity.Sale;
import com.example.backend.entity.Ticket;
import com.example.backend.repository.SaleRepository;
import com.example.backend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TicketService {
	private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
	
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SaleRepository saleRepository;
    
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> getAllTickets() {
        logger.debug("getAllTickets");
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(TicketResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TicketResponseDTO getTicketById(Integer id) {
        logger.debug("getTicketById");
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Ticket.class.getSimpleName() + " not found with id: " + id));
        return new TicketResponseDTO(ticket);
    }
    
    @Transactional
    public TicketResponseDTO createTicket(TicketDTO ticketDTO) {
        logger.debug("createTicket");
        if (ticketRepository.existsByTitre(ticketDTO.getTitre())) {
            throw new IllegalArgumentException("Un ticket avec le titre '" + ticketDTO.getTitre() + "' existe déjà.");
        }

        Ticket ticket = new Ticket();
        ticket.setTitre(ticketDTO.getTitre());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        if (ticketDTO.getSaleId() != null) {
            Sale sale = saleRepository.findById(ticketDTO.getSaleId())
                    .orElseThrow(() -> new RuntimeException("Sale not found with id: " + ticketDTO.getSaleId()));
            ticket.addSale(sale);
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        return new TicketResponseDTO(ticketRepository.findByIdWithSales(savedTicket.getId()).get());
    }
    
    @Transactional
    public TicketResponseDTO updateTicket(Integer id, TicketDTO ticketDTO) {
        logger.debug("updateTicket");

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        if (ticketDTO.getTitre() != null) {
            ticket.setTitre(ticketDTO.getTitre());
        }
        if (ticketDTO.getDescription() != null) {
            ticket.setDescription(ticketDTO.getDescription());
        }
        ticket.setUpdatedAt(LocalDateTime.now());

        if (ticketDTO.getSaleId() != null) {
            ticket.clearSales();
            Sale sale = saleRepository.findById(ticketDTO.getSaleId())
                    .orElseThrow(() -> new RuntimeException("Sale not found with id: " + ticketDTO.getSaleId()));
            ticket.addSale(sale);
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        return new TicketResponseDTO(ticketRepository.findByIdWithSales(savedTicket.getId()).get());
    }
    
    public void deleteTicket(Integer id) {
        logger.debug("deleteTicket");
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Ticket.class.getSimpleName() + " not found with id: " + id));
        
        ticketRepository.delete(ticket);
    }
}