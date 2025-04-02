package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Email;
import com.mycompany.myapp.repository.EmailRepository;
import com.mycompany.myapp.service.dto.EmailDTO;
import com.mycompany.myapp.service.mapper.EmailMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Email}.
 */
@Service
@Transactional
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    private final EmailRepository emailRepository;

    private final EmailMapper emailMapper;

    public EmailService(EmailRepository emailRepository, EmailMapper emailMapper) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    /**
     * Save a email.
     *
     * @param emailDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailDTO save(EmailDTO emailDTO) {
        LOG.debug("Request to save Email : {}", emailDTO);
        Email email = emailMapper.toEntity(emailDTO);
        email = emailRepository.save(email);
        return emailMapper.toDto(email);
    }

    /**
     * Update a email.
     *
     * @param emailDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailDTO update(EmailDTO emailDTO) {
        LOG.debug("Request to update Email : {}", emailDTO);
        Email email = emailMapper.toEntity(emailDTO);
        email = emailRepository.save(email);
        return emailMapper.toDto(email);
    }

    /**
     * Partially update a email.
     *
     * @param emailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmailDTO> partialUpdate(EmailDTO emailDTO) {
        LOG.debug("Request to partially update Email : {}", emailDTO);

        return emailRepository
            .findById(emailDTO.getId())
            .map(existingEmail -> {
                emailMapper.partialUpdate(existingEmail, emailDTO);

                return existingEmail;
            })
            .map(emailRepository::save)
            .map(emailMapper::toDto);
    }

    /**
     * Get all the emails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmailDTO> findAll() {
        LOG.debug("Request to get all Emails");
        return emailRepository.findAll().stream().map(emailMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the emails where PmEntreprise is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmailDTO> findAllWherePmEntrepriseIsNull() {
        LOG.debug("Request to get all emails where PmEntreprise is null");
        return StreamSupport.stream(emailRepository.findAll().spliterator(), false)
            .filter(email -> email.getPmEntreprise() == null)
            .map(emailMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the emails where PmEtablissement is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmailDTO> findAllWherePmEtablissementIsNull() {
        LOG.debug("Request to get all emails where PmEtablissement is null");
        return StreamSupport.stream(emailRepository.findAll().spliterator(), false)
            .filter(email -> email.getPmEtablissement() == null)
            .map(emailMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one email by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmailDTO> findOne(Long id) {
        LOG.debug("Request to get Email : {}", id);
        return emailRepository.findById(id).map(emailMapper::toDto);
    }

    /**
     * Delete the email by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Email : {}", id);
        emailRepository.deleteById(id);
    }
}
