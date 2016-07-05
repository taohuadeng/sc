package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Organization;
import com.tbc.elf.base.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 人员所属部门的业务逻辑操作
 *
 * @author ELF@TEAM
 * @since 2016年2月26日14:07:16
 */
public interface OrganizationService extends BaseService<Organization> {


    /**
     * 根据公司编号初始化部门
     *
     * @param corpCode 公司编号
     * @author SHIJIACHEN
     */
    public String initOrganization(String corpCode, String lastResultStatus);

     /**
     * 添加一个部门
     * @param Organization:部门实体
     * @throws java.lang.IllegalArgumentException
     *          <ul>
     *              <li>
     *                  当organization为null时
     *                  抛出IllegalArgumentException
     *              </li>
     *          </ul>
     */
    String addOrganization(Organization Organization);

    /**
     * 更新部门
     * @param Organization:部门实体
     * @throws java.lang.IllegalArgumentException
     *          <ul>
     *              <li>
     *                  当organization为null时
     *                  抛出IllegalArgumentException
     *              </li>
     *              <li>
     *                  当organization#organizationId为null时
     *                  抛出IllegalArgumentException
     *              </li>
     *          </ul>
     */
    void updateOrganization(Organization Organization);

   /**
     * 根据部门id列表查找部门
     * @param organizationIds:部门id列表
     * @throws java.lang.IllegalArgumentException
     *          <ul>
     *              <li>
     *                  当organizationIds为null时或者organizationIds长度为0时
     *                  抛出IllegalArgumentException
     *              </li>
     *          </ul>
     */
    List<Organization> findOrganization(Set<String> organizationIds);

    /**
     * 根据部门名称筛选部门节点列表JSON
     *
     * @param orgName
     * @return
     */
    public String findOrgNodeJson(String orgName, Integer limitOrgNum);

    /**
     * 根据组织ID获取对应的组织名称
     *
     * @return
     */
    public List<Organization> fillOrgNameByOrgId(List<Organization> orgList);

    /**
     * 根据部门ID获取其下的JSON树
     *
     * @param orgId
     * @return
     * @author SHIJIACHEN
     */ //TODO codereivew
    String findOrgTreeJson(String orgId, Boolean hasOrgCode);

    /**
     * 忽略权限获取所有（包括根部门）部门树,返回的json数据的形式如下：
     * <ul>
     * <li>id:部门id</li>
     * <li>nm:部门名称</li>
     * <li>sn:孩子节点</li>
     * </ul>
     *
     * @param orgId      部门树的根id
     * @param hasOrgCode 部门名称中是否包含部门编号
     * @return
     * @author leon modified by yinhao@HF
     * @since 2015/7/21
     */ //TODO codereview
    String findRootOrgTreeJson(String orgId, Boolean hasOrgCode);

    /**
     * 获取在指定部门下，除自身及其下属部门之外的所有部门树JSON
     *
     * @param currentRootOrgId 指定部门ID
     * @param currentOrgId     需除去的部门ID
     * @return String JSON树内容
     * @since 2015年4月1日 codeReview by GAOshan@HF
     */ //TODO codereview
    public String getAvailableOrgTreeJson(String currentRootOrgId, String currentOrgId);

    /**
     * 根据公司ID 公司ID在上下文中 查询部门体系的树
     * 返回的json数据包括以下属性：
     * <ol>
     * <li>id:部门id</li>
     * <li>text:部门名称</li>
     * <li>attribute:<ol>
     * <li>nodeType:Organization</li>
     * <li>info:部门全路径名</li>
     * </ol></li>
     * <li>children:孩子节点</li>
     * </ol>
     *
     * @param root        指定的根节点，为空表示公司根部门
     * @param hasOrgCode  部门名称中是否包含部门编号
     * @param hasNamePath 节点中是否包含部门全路径
     * @return
     * @author yinhao@HF
     * @since 2015/7/20
     */ //TODO codereview
    String findOrganizationTreeNew(String root, boolean hasOrgCode, boolean hasNamePath);

    /**
     * 根据公司ID在上下文中查询部门体系的树.
     *
     * @param root 根节点
     * @return 部门体系的树列表
     */ //TODO codereview
    //List<JsonTree> findOrganizationTreeList(String root);

    /**
     * 根据部门编号查找部门
     *
     * @param OrganizationCode
     * @return 部门对象
     * @author 杨涛
     */
    Organization findOrganizationByCode(String OrganizationCode);

    /**
     * 检测部门编号和名称不能重复
     * 可根据Organization中的OrganizationId判断是新增操作还是修改操作
     * List中存放所有的校验错误消息，如果List为空或SIZE为0表示校验正确
     * 检验错误信息KEY定义在UCI18NKey对象中获取
     *
     * @param Organization
     * @return 验证消息
     * @author 杨涛
     */
    Map<String, String> checkOrganization(Organization Organization);

    /**
     * 只校验部门名称同级下是否重复(节约性能，用于前台页面)
     *
     * @return
     * @author Shi JiaChen
     */
    List<String> checkOrganizationName(Organization Organization);

    /**
     * 根据当前用户获取已授权的所有部门,用于前端部门树节点下拉框的展示
     *
     * @param userId 当前用户
     * @return List<Organization> 部门列表
     * @author Shi JiaChen
     */
    String findOrganizationNodeByUser(String userId);

    /**
     * 获取除当前部门及其下属部门之外的所有部门，用于部门移动树的展示
     *
     * @param currentRootOrgId 授权部门ID
     * @param currentOrgId     当前部门ID
     * @return String JSON部门树
     * @author Shi JiaChen
     */ //TODO codereview
    public String getAvailableParentOrgTree(String currentRootOrgId, String currentOrgId);

    /**
     * 部门的上下平移
     *
     * @param OrganizationId 当前部门ID
     * @param isUp           是否上移
     * @author Shi JiaChen
     */
    void moveOrganization(String OrganizationId, Boolean isUp);

    /**
     * 对部门的删除增加判断（包括根部门不允许删除，部门下如果有对应人员不允许删除等）
     *
     * @param OrganizationId 当前部门ID
     * @author Shi JiaChen
     */
    void delete(String OrganizationId);

    /**
     * 根据当前部门获取可用人数
     *
     * @param removeOrgId 需要去除的部门ID 用于部门移动时计算移动后的使用人数
     * @return
     * @author Shi JiaChen
     */
    Integer getAvailableCountByOrg(Organization Organization, String removeOrgId);

    /**
     * 为限定人数的二级部门获取限定总人数
     */
    Long getPersonLimitCountForSecondLevel();

    /**
     * 为不限定人数的二级部门获取当前所有人数
     */
    Integer getPersonCountForSecondLevel();

    /**
     * 获得二级部门当前可用
     */
    Integer findAvailableCountForSecond();

    /**
     * 判断部门是否为二级部门
     */
    boolean judgeIsSecondLevel(String OrganizationId);

    /**
     * 获取当前部门下的实际人数（包含下级部门）
     */
    Long getCurrentUserCountByOrganizationIdWithChild(String OrganizationId);

    /**
     * 根据部门ID获取所有子部门(包括自身)
     * 当某组织没有任何子部门时，返回空List
     *
     * @param OrganizationId 部门id
     * @return List<String> 所有子部门id列表
     * @author Shi JiaChen
     * @modified by yinhao@HF
     * @since 2015/5/21
     */
    List<String> getChildIdsByOrganizationId(String OrganizationId);

    /**
     * 这个方法根据入参中的部门id列表，批量获得这些子部门id列表
     * 当这些组织没有任何子部门时，返回空List
     *
     * @param OrganizationIdList 父部门id列表
     * @return 所有子部门id列表（包括自身）
     * @author yinhao@YINHAO
     * @since 2015/5/21
     */
    List<String> getChildIdsByOrganizationdId(List<String> OrganizationIdList);

    /**
     * 根据部门ID获取所有父部门
     *
     * @param OrganizationId
     * @return
     */
    public List<String> getParentIdsByOrganizationId(String OrganizationId);

    /**
     * 查询公司下的根组织的Id
     *
     * @return
     * @author yangtao
     */
    public String findCorpRootId();

    /**
     * 增加一个部门
     *
     * @param Organization
     * @return
     * @author caoxiao
     */
    public String addOrganizationForUpgrade(Organization Organization);

    /**
     * 根据部门id查询部门是否存在
     *
     * @return
     */
    public boolean checkOrganizationIsExists(String OrganizationId);

    /**
     * 公司修改名称后同步修改根部门名称
     *
     * @param corpCode
     * @param corpName
     */
    public void updateOrgNameByCorp(String corpCode, String corpName);

    /**
     * 只校验部门编号是否重复
     *
     * @return
     * @author Shi JiaChen
     */
    List<String> checkOrganizationCode(Organization Organization);

    /**
     * 通过corpCoode获取组织对象
     *
     * @param corpCode
     * @return key=OrganizationCode字段、value=Organization对象
     * @author ZHANG Nan
     */
    public Map<String, Organization> getOrganizationByCorpCode(String corpCode);

    /**
     * 根据多个部门ID获取部门信息
     *
     * @param orgIds
     * @return
     */
    public Map<String, Organization> findOrganizationByIds(List<String> orgIds);

    /**
     * 根据部门path查询部门
     *
     * @param namePath
     * @return
     */
    public List<Organization> findOrganizationByNamePath(String namePath);

    /**
     * 根据部门名称PATH获取部门IDS
     *
     * @param namePath
     * @return
     */
    public Map<String, String> findOrganizationIdsByNamePath(List<String> namePath);

    /**
     * 数据同步API<br>
     * 批量同步部门<br>
     * 此方法会根据OrganizationCode判断Organization实例是否存在，从而进行新增/更新操作<br>
     * 注意：请按照组织结构从根到最外节点的顺序排列list参数，否则可能会找不到父组织导致更新失败<br>
     * 请将唯一区分部门的编号存入OrganizationCode属性并提交<br>
     * 请查阅文档了解Organization对象必需字段<br>
     *
     * @param Organizations
     * @return 部门编号与系统分配部门IDmap
     */
    public Map<String, String> batchSyncOrganization(List<Organization> Organizations);

    /**
     * 数据同步API<br>
     * 同步部门<br>
     * 根据传入的Organization对象<br>
     *
     * @param Organization
     * @return
     */
    public String syncOrganization(Organization Organization);

    /**
     * 如果某个组织下，既没有子组织也没有人员则删除该组织<br>
     *
     * @param corpCode
     * @return
     */
    public void deleteOrgByNoChild(String corpCode);

    /**
     * 删除公司某个没有组织也没有人员的部门
     * @param corpCode
     * @param organizationId
     */
    public void deleteOrgByNoChildAndUser(String corpCode, String organizationId);

    /**
     * 根据父ID，部门名称，得到相同名字的兄弟id
     *
     * @param parentId
     * @param orgName
     * @return
     */
    public String getSameNameBrotherId(String parentId, String orgName);


    /**
     * 查询三级部门的部门ID
     */
    public List<String> findOnlyThreeLevelOrgIds();

    /**
     * 根据相对根部门的层级深度获取根部门到该深度部门的集合
     *
     * @param level 层级深度
     * @return 排序好部门
     */
    public List<Organization> getOrganizationsByLevel(Integer level);

    /**
     * 根据部门负责人userId获取部门
     *
     * @param chargeUserId 部门负责人Id
     * @return 排序好部门
     */
    public List<Organization> getOrganizationsByChargeUserId(String chargeUserId);

    /**
     * 根据部门ID获取部门，如果获取的部门深度大于level则返回其父级部门
     *
     * @param OrganizationId
     * @param level
     * @return
     */
    public Organization getOrganizationByIdAndLevel(String OrganizationId, Integer level);

    public List<String> getOrganizationFirstChildrenById(String OrganizationId);

    /**
     * 根据orgname查询公司的二级部门
     *
     * @param keyword
     * @param page
     * @return
     */
    //public Page<Organization> findOrgNodePage(String keyword, Page<Organization> page);

    /**
     * 根据公司编码找到当前公司根部门
     * <p>如果公司编码为空，则找当前公司的根部门</p>
     *
     * @param corpCode 公司编码
     * @return 当前公司根部门
     */
    Organization findRootOrgByCorpCode(String corpCode);

    /**
     * 获取当前部门下的实际人数
     * <p>hasChild传入true则为包含子部门，false则为不包含子部门</p>
     *
     * @param OrganizationId 组织id
     * @return 当前部门包含下级部门的实际人数
     */
    Long getUserByOrganizationId(String OrganizationId, boolean hasChild);

    /**
     * 计算部门的父部门的当前人数， 当部门移动时， 人员移动时 增加人员时 删除人员时，都要调用此方法
     *
     * @param OrganizationId 部门id
     * @param count          部门人数
     * @param hasMe          是否包含自己
     * @refactor Gaoshan@HF
     * @since 2015年3月31日
     */
    void calculateUserCount(String OrganizationId, Integer count, boolean hasMe, Map<String, Organization> mapOrganizationIdToMap);

    /**
     * 验证当前公司的部门名称是否重复
     *
     * @param Organization 部门
     * @return true为重复，false为不重复
     */
    boolean checkOrgName(Organization Organization);

    void synOrgNamePath();

    /**
     * 列出当前公司所有二级部门(baxf宝安消防局定制)
     *
     * @return 当前公司所有二级部门
     * <ul>
     * Organization包含属性：
     * <li>OrganizationId:部门Id</li>
     * <li>OrganizationName:部门名称</li>
     * </ul>
     * @author suran@HF
     * @since 2015-5-13
     */
    List<Organization> getSecOrgNodeList();

    /**
     * 查询指定等级的部门信息列表
     *
     * @param isLimit  部门是否限制人数，为null时，表示查询所有的
     * @param orgLevel 部门等级，比如一级部门,当orgLevel<1时，默认为1
     * @return 符合条件的部门信息列表
     * @ahthor yeWanQing@HF
     * @since 2015年6月2日
     */
    List<Organization> getLevelOrganizationList(Boolean isLimit, int orgLevel);

    /**
     * 根据指定部门id获取该部门对应的根部门下的二级部门和三级部门id列表
     *
     * @param orgId 部门id
     * @return 对应的二级部门和三级部门Ids按顺序, 二级在前面
     * @throws java.lang.IllegalArgumentException 当orgId为空时
     * @author suRan@HF
     * @since 2015-6-9
     */
    List<String> getFirstAndSecLevelOrgIds(String orgId);

    /**
     * 这个方法获得入参中部门id对应的部门路径名
     *
     * @param orgIds 部门id集合
     * @return key-部门id,value-部门路径名
     * @author yinhao@HF
     * @since 2015/6/11
     */
    Map<String, String> getOrgIdNamePathMap(Set<String> orgIds);

    /**
     * 根据部门ID获取其下指定深度的JSON树
     *
     * @param orgId 指定做为根部门的部门id，为空时：
     *              <ol>
     *              <li>如果当前用户为系统管理员，则表示公司根部门id</li>
     *              <li>如果当前用户为培训管理员，程序则把根部门处理成该培训管理员管理的所有部门</li>
     *              </ol>
     * @param depth 指定根部门下部门树的查询最大深度
     *              比如，depth为2时，返回的树中只包括根节点，根节点的子节点和根节点的孙子节点
     * @return 指定部门下的部门树json数据, 每个节点包含如下属性：
     * <ul>
     * <li>id:部门id</li>
     * <li>nm:部门名称</li>
     * <li>sn:孩子节点集合的引用</li>
     * </ul>
     * @author yinhao@HF
     * @since 2015/6/18
     */
    String findOrgTreeJsonByDepth(String orgId, int depth);

    /**
     * 获得当前公司所有组织列表集合信息
     *
     * @return 组织列表集合 组织信息包含如下属性：
     * <ul>
     * <li>OrganizationId</li>
     * <li>OrganizationName</li>
     * <li>parentId</li>
     * <li>namePath</li>
     * </ul>
     */
    List<Organization> getAllOrganization();

    /**
     * 保存组织列表信息
     *
     * @param Organizations 组织列表
     */
    void save(List<Organization> Organizations);


    /**
     * 这个方法根据入参传入的部门id列表，在当前公司获得每个部门下的人员id列表，
     * 并以"部门id"到"该部门下人员id列表"的映射的形式返回
     *
     * @param orgIdList         指定的部门id列表
     * @param includeChildOrg   返回结果的value中是否包含子部门的人员id
     * @param accountStatusList 人员状态筛选条件
     * @return key(orgId, 部门id) - value(userIds, key代表的部门下的人员id列表),人员id按最后修改时间降序排列
     * @throws java.lang.IllegalArgumentException <ul>
     *                                            <li>orgIdList为空</li>
     *                                            <li>accountStatusList为空</li>
     *                                            </ul>
     */
    Map<String, List<String>> getUserIdListGroupByOrgId(List<String> orgIdList, boolean includeChildOrg, List<String> accountStatusList);

    /**
     * 根据组织id列表找到对应的组织信息
     *
     * @param orgIds 组织列表id
     * @return Map<String, Organization> key为组织id，value为组织信息，其中包含的属性有：
     * <ul>
     * <li>OrganizationId:组织id</li>
     * <li>namePath:部门全路径</li>
     * <li>OrganizationCode:组织编号</li>
     * </ul>
     * @throws java.lang.IllegalArgumentException 当orgIds为空时
     * @author yeWanQing@HF
     * @since 2015年7月12日
     */
    Map<String, Organization> getOrgIdAndOrganizationMap(Set<String> orgIds);
}
