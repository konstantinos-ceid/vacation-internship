package gr.knowledge.internship.vacation.utility;

import org.springframework.stereotype.Service;

@Service
public class Util {

    public static void validateUpdateInput(Long payloadId, Long paramId, Boolean isExistingEntity) {
        checkIdsCorelation(payloadId, paramId);
        checkExistanceOfEntity(isExistingEntity);
    }


    public static void validateDeleteInput(Boolean isExistingEntity) {
        checkExistanceOfEntity(isExistingEntity);
    }

    private static void checkExistanceOfEntity(Boolean isExistingEntity) {
        if (!isExistingEntity) {
            throw new RuntimeException("Non existing entity for given id");
        }
    }

    private static void checkIdsCorelation(Long payloadId, Long paramId) {
        if (payloadId == null || paramId == null) {
            throw new RuntimeException("Null id");
        }
        if (!payloadId.equals(paramId)) {
            throw new RuntimeException("Invalid id");
        }
    }
}
