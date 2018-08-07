package edp.davinci.controller;


import edp.core.annotation.CurrentUser;
import edp.core.enums.HttpCodeEnum;
import edp.davinci.common.controller.BaseController;
import edp.davinci.core.common.Constants;
import edp.davinci.core.common.ResultMap;
import edp.davinci.dto.dashboardDto.DashboardCreate;
import edp.davinci.dto.dashboardDto.DashboardPortalCreate;
import edp.davinci.dto.dashboardDto.DashboardPortalUpdate;
import edp.davinci.dto.dashboardDto.MemDashboardWidgetCreate;
import edp.davinci.model.Dashboard;
import edp.davinci.model.MemDashboardWidget;
import edp.davinci.model.User;
import edp.davinci.service.DashboardPortalService;
import edp.davinci.service.DashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(value = "/dashboardPortals", tags = "dashboardPortals", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ApiResponses(@ApiResponse(code = 404, message = "dashboardPortal not found"))
@Slf4j
@RestController
@RequestMapping(value = Constants.BASE_API_PATH + "/dashboardPortals", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DashboardController extends BaseController {

    @Autowired
    private DashboardPortalService dashboardPortalService;

    @Autowired
    private DashboardService dashboardService;

    /**
     * 获取dashboardPortal列表
     *
     * @param projectId
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "get dashboardPortals")
    @GetMapping
    public ResponseEntity getDashboardPortals(@RequestParam Long projectId,
                                              @ApiIgnore @CurrentUser User user,
                                              HttpServletRequest request) {
        if (invalidId(projectId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid project id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }
        try {
            ResultMap resultMap = dashboardPortalService.getDashboardPortals(projectId, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }


    /**
     * 获取dashboard列表
     *
     * @param id
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "get dashboards")
    @GetMapping("/{id}/dashboards")
    public ResponseEntity getDashboards(@PathVariable Long id,
                                        @ApiIgnore @CurrentUser User user,
                                        HttpServletRequest request) {
        if (invalidId(id)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        try {
            ResultMap resultMap = dashboardService.getDashboards(id, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }

    /**
     * 获取dashboard下widgets关联信息列表
     *
     * @param portalId
     * @param dashboardId
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "get dashboard widgets")
    @GetMapping("/{portalId}/dashboards/{dashboardId}/widgets")
    public ResponseEntity getDashboardMemWidgets(@PathVariable("portalId") Long portalId,
                                                 @PathVariable("dashboardId") Long dashboardId,
                                                 @ApiIgnore @CurrentUser User user,
                                                 HttpServletRequest request) {
        if (invalidId(portalId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid dashboard portal id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        if (invalidId(portalId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid dashboard id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        try {
            ResultMap resultMap = dashboardService.getDashboardMemWidgets(portalId, dashboardId, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }

    /**
     * 新建dashboardPortal
     *
     * @param dashboardPortalCreate
     * @param bindingResult
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "create dashboard portal")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createDashboardPortal(@Valid @RequestBody DashboardPortalCreate dashboardPortalCreate,
                                                @ApiIgnore BindingResult bindingResult,
                                                @ApiIgnore @CurrentUser User user,
                                                HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        try {
            ResultMap resultMap = dashboardPortalService.createDashboardPortal(dashboardPortalCreate, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }


    /**
     * 更新dashboardPortal
     *
     * @param id
     * @param dashboardPortalUpdate
     * @param bindingResult
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "update dashboard portal")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateDashboardPortal(@PathVariable Long id,
                                                @Valid @RequestBody DashboardPortalUpdate dashboardPortalUpdate,
                                                @ApiIgnore BindingResult bindingResult,
                                                @ApiIgnore @CurrentUser User user,
                                                HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        if (invalidId(id) || !dashboardPortalUpdate.getId().equals(id)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        try {
            ResultMap resultMap = dashboardPortalService.updateDashboardPortal(dashboardPortalUpdate, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }


    /**
     * 删除dashboardPortal
     *
     * @param id
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "delete dashboard portal")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDashboardPortal(@PathVariable Long id,
                                                @ApiIgnore @CurrentUser User user,
                                                HttpServletRequest request) {

        if (invalidId(id)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        try {
            ResultMap resultMap = dashboardPortalService.deleteDashboardPortal(id, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }


    /**
     * 新建dashboard
     *
     * @param portalId
     * @param dashboardCreate
     * @param bindingResult
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "create dashboard")
    @PostMapping(value = "/{id}/dashboards", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createDashboard(@PathVariable("id") Long portalId,
                                          @Valid @RequestBody DashboardCreate dashboardCreate,
                                          @ApiIgnore BindingResult bindingResult,
                                          @ApiIgnore @CurrentUser User user,
                                          HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        if (invalidId(portalId) || !dashboardCreate.getDashboardPortalId().equals(portalId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid dashboard portal id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        try {
            ResultMap resultMap = dashboardService.createDashboard(dashboardCreate, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }

    /**
     * 修改dashboard
     *
     * @param portalId
     * @param dashboards
     * @param bindingResult
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "update dashboards")
    @PutMapping(value = "{id}/dashboards", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateDashboards(@PathVariable("id") Long portalId,
                                           @Valid @RequestBody Dashboard[] dashboards,
                                           @ApiIgnore BindingResult bindingResult,
                                           @ApiIgnore @CurrentUser User user,
                                           HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        for (Dashboard dashboard : dashboards) {
            if (!dashboard.getDashboardPortalId().equals(portalId)) {
                ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid dashboard portal id");
                return ResponseEntity.status(resultMap.getCode()).body(resultMap);
            }
        }

        try {
            ResultMap resultMap = dashboardService.updateDashboards(portalId, dashboards, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }


    /**
     * 删除dashboard
     *
     * @param dashboardId
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "delete dashboard")
    @DeleteMapping("/dashboards/{dashboardId}")
    public ResponseEntity deleteDashboard(@PathVariable Long dashboardId,
                                          @ApiIgnore @CurrentUser User user,
                                          HttpServletRequest request) {

        if (invalidId(dashboardId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        try {
            ResultMap resultMap = dashboardService.deleteDashboard(dashboardId, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }


    /**
     * 在dashboard下新建widget关联
     *
     * @param portalId
     * @param dashboardId
     * @param memDashboardWidgetCreate
     * @param bindingResult
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "create dashboard widget relation")
    @PostMapping(value = "/{portalId}/dashboards/{dashboardId}/widgets", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createMemDashboardWidget(@PathVariable("portalId") Long portalId,
                                                   @PathVariable("dashboardId") Long dashboardId,
                                                   @Valid @RequestBody MemDashboardWidgetCreate memDashboardWidgetCreate,
                                                   @ApiIgnore BindingResult bindingResult,
                                                   @ApiIgnore @CurrentUser User user,
                                                   HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        if (invalidId(portalId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid dashboard portal id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        if (invalidId(dashboardId) || !dashboardId.equals(memDashboardWidgetCreate.getDashboardId())) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid dashboard id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        try {
            ResultMap resultMap = dashboardService.createMemDashboardWidget(portalId, dashboardId, memDashboardWidgetCreate, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }


    /**
     * 修改dashboard下的widget关联信息
     *
     * @param relationId
     * @param memDashboardWidget
     * @param bindingResult
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "update dashboard widget relation")
    @PutMapping(value = "/dashboards/widgets/{relationId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateMemDashboardWidget(@PathVariable Long relationId,
                                                   @Valid @RequestBody MemDashboardWidget memDashboardWidget,
                                                   @ApiIgnore BindingResult bindingResult,
                                                   @ApiIgnore @CurrentUser User user,
                                                   HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        if (invalidId(relationId) || !memDashboardWidget.getId().equals(relationId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid  id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        try {
            ResultMap resultMap = dashboardService.updateMemDashboardWidget(memDashboardWidget, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }


    /**
     * 删除dashboard下的widget关联信息
     *
     * @param relationId
     * @param bindingResult
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "delete dashboard widget relation")
    @DeleteMapping(value = "/dashboards/widgets/{relationId}")
    public ResponseEntity deleteMemDashboardWidget(@PathVariable Long relationId,
                                                   @ApiIgnore BindingResult bindingResult,
                                                   @ApiIgnore @CurrentUser User user,
                                                   HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        try {
            ResultMap resultMap = dashboardService.deleteMemDashboardWidget(relationId, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }


    /**
     * 分享dashboard
     *
     * @param dashboardId
     * @param username
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "share dashboard")
    @GetMapping("/dashboards/{dashboardId}/share")
    public ResponseEntity shareDashboard(@PathVariable Long dashboardId,
                                         @RequestParam(required = false) String username,
                                         @ApiIgnore @CurrentUser User user,
                                         HttpServletRequest request) {

        if (invalidId(dashboardId)) {
            ResultMap resultMap = new ResultMap(tokenUtils).failAndRefreshToken(request).message("Invalid  id");
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        }

        try {
            ResultMap resultMap = dashboardService.shareDashboard(dashboardId, username, user, request);
            return ResponseEntity.status(resultMap.getCode()).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpCodeEnum.SERVER_ERROR.getCode()).body(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
    }

}
