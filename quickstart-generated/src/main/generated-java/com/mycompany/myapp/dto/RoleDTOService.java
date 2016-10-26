/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-angular:src/main/java/dto/EntityDTOService.java.e.vm
 */
package com.mycompany.myapp.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.Role;
import com.mycompany.myapp.domain.Role_;
import com.mycompany.myapp.dto.support.PageRequestByExample;
import com.mycompany.myapp.dto.support.PageResponse;
import com.mycompany.myapp.repository.RoleRepository;

/**
 * A simple DTO Facility for Role.
 */
@Service
public class RoleDTOService {

    @Inject
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public RoleDTO findOne(Integer id) {
        return toDTO(roleRepository.findOne(id));
    }

    @Transactional(readOnly = true)
    public List<RoleDTO> complete(String query, int maxResults) {
        List<Role> results = roleRepository.complete(query, maxResults);
        return results.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PageResponse<RoleDTO> findAll(PageRequestByExample<RoleDTO> req) {
        Example<Role> example = null;
        Role role = toEntity(req.example);

        if (role != null) {
            ExampleMatcher matcher = ExampleMatcher.matching() //
                    .withMatcher(Role_.roleName.getName(), match -> match.ignoreCase().startsWith());

            example = Example.of(role, matcher);
        }

        Page<Role> page;
        if (example != null) {
            page = roleRepository.findAll(example, req.toPageable());
        } else {
            page = roleRepository.findAll(req.toPageable());
        }

        List<RoleDTO> content = page.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), content);
    }

    /**
     * Save the passed dto as a new entity or update the corresponding entity if any.
     */
    @Transactional
    public RoleDTO save(RoleDTO dto) {
        if (dto == null) {
            return null;
        }

        Role role;
        if (dto.isIdSet()) {
            role = roleRepository.findOne(dto.id);
        } else {
            role = new Role();
        }

        role.setRoleName(dto.roleName);

        return toDTO(roleRepository.save(role));
    }

    /**
     * Converts the passed role to a DTO.
     */
    public RoleDTO toDTO(Role role) {
        return toDTO(role, 1);
    }

    /**
     * Converts the passed role to a DTO. The depth is used to control the
     * amount of association you want. It also prevents potential infinite serialization cycles.
     *
     * @param role
     * @param depth the depth of the serialization. A depth equals to 0, means no x-to-one association will be serialized.
     *              A depth equals to 1 means that xToOne associations will be serialized. 2 means, xToOne associations of
     *              xToOne associations will be serialized, etc.
     */
    public RoleDTO toDTO(Role role, int depth) {
        if (role == null) {
            return null;
        }

        RoleDTO dto = new RoleDTO();

        dto.id = role.getId();
        dto.roleName = role.getRoleName();
        if (depth-- > 0) {
        }

        return dto;
    }

    /**
     * Converts the passed dto to a Role.
     * Convenient for query by example.
     */
    public Role toEntity(RoleDTO dto) {
        return toEntity(dto, 1);
    }

    /**
     * Converts the passed dto to a Role.
     * Convenient for query by example.
     */
    public Role toEntity(RoleDTO dto, int depth) {
        if (dto == null) {
            return null;
        }

        Role role = new Role();

        role.setId(dto.id);
        role.setRoleName(dto.roleName);
        if (depth-- > 0) {
        }

        return role;
    }
}