# Authority management instruction

## Design idea

It is different from common administrator centralization managed privileges; authorities in this project are authorized by groups, and a manner for administrators manage groups, group leader manage group members is adopted. This kind of hierarchical authority management manner reduce administrator’s approval burden greatly, flexibility for group autonomy is also improved.

Structure chart of authority management:
Among which, authority of zookeeper takes node as a unit, a group applies for authority to the administrator, it is approved and authorized by super administrator. Group members is consist of users, users could choose to establish new groups or join into existing groups.

![Permission design](images/permission-design.png)

## Function instruction

### Super administrator

All super administrators belong to a specific team - admin, taking charge for the following managements:

- Check authority application of a group (approve/refuse)
- Authorize some authorities on a node to a group

### Authority

Authority is divided with node as the granularity and handed out with group as a unit.

- Node authority: Each node is uniquely determined by cluster and route, if father node authority is possessed, child node authority at that node is also possessed

### Group

#### Group roles

Group members are divided into different roles, there are now 3 roles:

- Master: group manager. To manage group member, apply for authority to administrator, it has the authority to add, delete and modify nodes
- Developer: developer. It have the authority to add and modify nodes, without a deleting authority
- Member: Same to developer temporarily.

A group may have the authority of several nodes, node authorities are shared by all of the members within a group.
Members of a group can all add and change nodes authorized to the group, but only master of a group could delete nodes.

#### Group management

- Deal with user's application for join in a group
- Add/Delete group member
- Modify roles of group members
- Apply for new node authority


### Users

Before operating nodes, a user should apply for authority in advance, there are mainly two methods:

- Establish a new group: A user will send out an authority application to current node when establishing new groups, it will be OK after the approval of the administrator
- Apply for join in a group: A user apply for join in any group with node authority, it will be OK after the approval of the master of a group

## Usage

This part mainly introduces functional inlet of the system, with the effect screenshot attached.

Domain name and the port number are serverName:serverPort, only relative link will be provided in the following text.

### Super administrator

- Cluster management: `/admin`

- Authority management: `/permission`

### Group management

- Group list belongs to a user: `/teams`

- Group member management: `/teams/{teamId}/manage`

- Group authority management: `/teams/{teamId}/permission`


### User application

- User applies authorities: `/teams/apply?path=/zookeeper&cluster=local_test`