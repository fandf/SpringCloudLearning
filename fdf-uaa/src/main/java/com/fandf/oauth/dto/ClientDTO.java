package com.fandf.oauth.dto;

import com.fandf.oauth.model.Client;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

/**
 * @author fandongfeng
 * @date 2022/7/11 15:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientDTO extends Client {
    private static final long serialVersionUID = 8600612483766283945L;

    private List<Long> permissionIds;

    private Set<Long> serviceIds;

}
