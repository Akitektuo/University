package ro.ubb.truckers.domain.entity;

import org.json.JSONObject;
import ro.ubb.truckers.util.Pair;

public class UserCompany extends BaseEntity<Pair<Integer, Integer>> {
    private boolean manager;

    public UserCompany(Pair<Integer, Integer> compositeId, boolean manager) {
        super(compositeId);
        this.manager = manager;
    }

    public UserCompany(int userId, int companyId, boolean manager) {
        super(new Pair<>(userId, companyId));
        this.manager = manager;
    }

    public static UserCompany fromJSON(String jsonString) {
        var json = new JSONObject(jsonString);

        var userId = json.getInt("userId");
        var companyId = json.getInt("companyId");
        var manager = json.getBoolean("manager");

        return new UserCompany(userId, companyId, manager);
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public int getUserId() {
        return getId().getFirst();
    }

    public void setUserId(int userId) {
        getId().setFirst(userId);
    }

    public int getCompanyId() {
        return getId().getSecond();
    }

    public void setCompanyId(int companyId) {
        getId().setSecond(companyId);
    }

    @Override
    public String toString() {
        return String.format("UserCompany(userId=%s, companyId=%s, isManager=%s)",
                getId().getFirst(), getId().getSecond(), manager);
    }

    @Override
    public String toJSON() {
        var json = new JSONObject();

        json.put("userId", getUserId())
                .put("companyId", getCompanyId())
                .put("manager", manager);

        return json.toString();
    }
}
