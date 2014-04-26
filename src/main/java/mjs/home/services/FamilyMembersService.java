package mjs.home.services;

import mjs.common.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service used to retrieve the current list of users.
 */
@Service
@Transactional
public class FamilyMembersService extends BaseService {

    public FamilyMembersService() {
        super("mjs.model.FamilyMember", "family members", "fname+lname", "family_members_pk", "FamilyMember");
    }

}
