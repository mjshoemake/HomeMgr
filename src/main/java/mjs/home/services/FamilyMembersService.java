package mjs.home.services;

import mjs.common.exceptions.ModelException;
import mjs.model.FamilyMember;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring service used to retrieve the current list of users.
 */
@Service
@Transactional
public class FamilyMembersService extends BaseService {

    /**
     * The log4j logger to use when writing log messages.
     */
    protected static final Logger log = Logger.getLogger("Service");

    public FamilyMembersService() {
        super(FamilyMember.class);
    }

    public String saveFamilyMember(FamilyMember familyMember) throws ModelException {
        try {
            if (familyMember != null) {
                log.debug("Saving family member " + familyMember.getName() + "...");
                String newId = save(familyMember);
                log.debug("   Saved.  Returning pk: " + newId);
                return newId;
            } else {
                throw new Exception("Expected a valid family member but received null.");
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to save the specified family member.", e);
            throw new ModelException("Unable to save the specified family member. " + e.getMessage());
        }
    }

    public FamilyMember getFamilyMemberByPK(int id) throws ModelException {
        try {
            List<FamilyMember> familyMembers = findByCriteria(Restrictions.eq("family_member_pk", new Integer(id)));
            if (familyMembers.size() == 1) {
                return familyMembers.get(0);
            } else if (familyMembers.size() > 1) {
                throw new Exception("More than one family member returned matching this ID but only one is expected.");
            } else if (familyMembers.size() == 0) {
                throw new Exception("No family members found that match the specified ID.");
            } else {
                throw new Exception("Unexpected error. Number of family members returned: " + familyMembers.size());
            }
        } catch (ModelException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to retrieve this family member (" + id + ").", e);
            throw new ModelException("Unable to retrieve this family member (" + id + "). " + e.getMessage());
        }
    }

    public List getAllFamilyMembers() throws ModelException {
        try {
            return getAll("from FamilyMember");
        } catch (Exception e) {
            log.error("Unable to retrieve the family member list.", e);
            throw new ModelException("Unable to retrieve the family member list. " + e.getMessage());
        }
    }

    public void deleteFamilyMember(FamilyMember familyMember) throws ModelException {
        try {
            if (familyMember != null) {
                delete(familyMember);
            } else {
                throw new Exception("Expected a valid family member but received null.");
            }
        } catch (Exception e) {
            log.error("Unable to delete this family member.", e);
            throw new ModelException("Unable to delete this family member. " + e.getMessage());
        }
    }
}
