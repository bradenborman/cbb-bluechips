package Borman.cbbbluechips.controllers;

import Borman.cbbbluechips.models.usergroups.AddUserToGroupRequest;
import Borman.cbbbluechips.models.usergroups.RemoveUserFromGroupRequest;
import Borman.cbbbluechips.services.UserGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController extends AuthenticatedController {

    UserGroupService userGroupService;

    public GroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    @PostMapping("/attempt-to-join")
    public ResponseEntity<String> joinGroup(@RequestBody AddUserToGroupRequest addUserToGroupRequest) {
        addUserToGroupRequest.setUserId(retrieveLoggedInUserId());
        userGroupService.addUserIdToGroup(addUserToGroupRequest);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/attempt-to-leave")
    public ResponseEntity<String> leaveGroup(@RequestBody RemoveUserFromGroupRequest removeUserFromGroupRequest) {
        removeUserFromGroupRequest.setUserId(retrieveLoggedInUserId());
        userGroupService.removeUserFromGroup(removeUserFromGroupRequest);
        return ResponseEntity.ok("Success");
    }

}