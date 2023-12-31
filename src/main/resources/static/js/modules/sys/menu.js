var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rapp',
    data:{
        showList: true,
        title: null,
        menu:{
            parentName:null,
            parentId:0,
            type:1,
            orderNum:0
        }
    },
    methods: {
        getMenu: function(){
            //加載菜單樹
            $.get(baseURL + "/sys/menu/select", function(r){
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
                var node = ztree.getNodeByParam("id", vm.menu.parentId);
                ztree.selectNode(node);

                vm.menu.parentName = node.name;
            })
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.menu = {parentName:null,parentId:0,type:1,orderNum:0};
            vm.getMenu();
        },
        update: function () {
            var menuId = getMenuId();
            if(menuId == null){
                return ;
            }

            $.get(baseURL + "/sys/menu/info/"+menuId, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.menu = r.menu;

                vm.getMenu();
            });
        },
        del: function () {
            var menuId = getMenuId();
            if(menuId == null){
                return ;
            }

            confirm('確定刪除此資料？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "/sys/menu/delete",
                    data: "menuId=" + menuId,
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var url = vm.menu.id == null ? "/sys/menu/save" : "/sys/menu/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.menu),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        menuTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "選取選單",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['確定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //選擇上級菜單
                    vm.menu.parentId = node[0].id;
                    vm.menu.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            Menu.table.refresh();
        }
    }
});

var Menu = {
    id: "menuTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        /*{title: '菜單id', field: 'id', visible: false, align: 'center', valign: 'middle', width: '80px'},*/
        {title: '選單名稱', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '160px'},
        {title: '母選單', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '160px'},
        {title: '圖形', field: 'icon', align: 'center', valign: 'middle', sortable: true, width: '60px', formatter: function(item, index){
            return item.icon == null ? '' : '<i class="'+item.icon+' fa-lg"></i>';
        }},
        {title: '類型', field: 'type', align: 'center', valign: 'middle', sortable: true, width: '60px', formatter: function(item, index){
            if(item.type === 0){
                return '<span class="label label-primary">目錄</span>';
            }
            if(item.type === 1){
                return '<span class="label label-success">選單</span>';
            }
            if(item.type === 2){
                return '<span class="label label-warning">按鈕</span>';
            }
        }},
        {title: '排序', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '60px'},
        {title: '選單URL', field: 'url', align: 'center', valign: 'middle', sortable: true, width: '160px'},
        {title: '授權', field: 'perms', align: 'center', valign: 'middle', sortable: true, width: '160px'}]
    return columns;
};

function getMenuId () {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("請選取一筆資料");
        return;
    } else {
        return selected[0].id;
    }
}

$(function () {
    var colunms = Menu.initColumn();
    var table = new TreeTable(Menu.id, baseURL + "/sys/menu/list", colunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    Menu.table = table;
});
