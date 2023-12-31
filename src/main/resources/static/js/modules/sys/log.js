$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + '/sys/log/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', key: true, hidden: true },
			{ label: '使用者名稱', name: 'username', width: 50 }, 			
			{ label: '使用者操作', name: 'operation', width: 70 }, 			
			{ label: 'IP地址', name: 'ip', width: 70 }, 			
			{ label: '創建時間', name: 'createTime',index: 'create_time', width: 90 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隱藏grid底部滾動條
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rapp',
	data:{
		q:{
			key: null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reload: function (event) {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		}
	}
});