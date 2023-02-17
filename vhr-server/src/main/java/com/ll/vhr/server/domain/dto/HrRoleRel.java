package com.ll.vhr.server.domain.dto;

import com.ll.vhr.server.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrRoleRel extends Role {
    public Integer hrid;
}
