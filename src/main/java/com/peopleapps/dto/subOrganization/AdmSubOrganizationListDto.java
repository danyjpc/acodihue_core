package com.peopleapps.dto.subOrganization;

import java.time.LocalDateTime;

public class AdmSubOrganizationListDto {

    public final Long sub_organization_id;
    public final Long organization_id;
    public final String organization_name;
    public final String organization_commercial_name;
    public final Long organization_child_id;
    public final String organization_child_name;
    public final String organization_child_commercial_name;
    public final LocalDateTime date_created;
    public final Long created_by_id;

    public AdmSubOrganizationListDto(Long sub_organization_id, Long organization_id, String organization_name, String organization_commercial_name, Long organization_child_id, String organization_child_name, String organization_child_commercial_name, LocalDateTime date_created, Long created_by_id) {
        this.sub_organization_id = sub_organization_id;
        this.organization_id = organization_id;
        this.organization_name = organization_name;
        this.organization_commercial_name = organization_commercial_name;
        this.organization_child_id = organization_child_id;
        this.organization_child_name = organization_child_name;
        this.organization_child_commercial_name = organization_child_commercial_name;
        this.date_created = date_created;
        this.created_by_id = created_by_id;
    }
}
