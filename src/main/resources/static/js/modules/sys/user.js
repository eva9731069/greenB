$(function () {
	$("#jqGrid").jqGrid({
		url: baseURL + '/sys/user/list',
		datatype: "json",
		colModel: [
			{ label: '使用者編號', name: 'id', index: 'id', key: true, hidden: true },
			{ label: '使用者名稱', name: 'username', width: 75 },
			// { label: '密碼', name: 'password', width: 75 },
			{ label: 'email', name: 'email', width: 90 },
			// { label: '英文名稱', name: 'enName', width: 100 },
			{ label: '身分證字號', name: 'personId', width: 100 },
			{
				label: '性別', name: 'gender', width: 100, formatter: function (value, options, row) {
					if (value === '0') {
						return '<span class="label label-primary">男生</span>';
					} else if (value === '1') {
						return '<span class="label label-danger">女生</span>';
					}
				}
			},
			{ label: '生日', name: 'birth', width: 100 },

			{
				label: '血型', name: 'bloodType', width: 80, formatter: function (value, options, row) {
					if (value === '0') {
						return '<span class="label label-primary">O型</span>';
					} else if (value === '1') {
						return '<span class="label label-danger">AB型</span>';
					} else if (value === '2') {
						return '<span class="label label-warning">A型</span>';
					} else {
						return '<span class="label label-default">B型</span>';
					}
				}
			},

			// {
			// 	label: '身分類別', name: 'personType', width: 80, formatter: function (value, options, row) {
			// 		if (value === '0') {
			// 			return '<span class="label label-primary">一般</span>';
			// 		} else if (value === '1') {
			// 			return '<span class="label label-danger">原住民</span>';
			// 		} else if (value === '2') {
			// 			return '<span class="label label-warning">身心障礙</span>';
			// 		} else {
			// 			return '<span class="label label-default">新住民</span>';
			// 		}
			// 	}
			// },
			// {
			// 	label: '兵役狀況', name: 'soldierType', width: 80, formatter: function (value, options, row) {
			// 		if (value === '0') {
			// 			return '<span class="label label-primary">未服役</span>';
			// 		} else if (value === '1') {
			// 			return '<span class="label label-danger">現役</span>';
			// 		} else if (value === '2') {
			// 			return '<span class="label label-warning">替代役</span>';
			// 		} else if (value === '3') {
			// 			return '<span class="label label-warning">免疫</span>';
			// 		} else {
			// 			return '<span class="label label-default">已服役</span>';
			// 		}
			// 	}
			// },
			// {
			// 	label: '婚姻狀態', name: 'marriedType', width: 80, formatter: function (value, options, row) {
			// 		if (value === '0') {
			// 			return '<span class="label label-primary">未婚</span>';
			// 		} else if (value === '1') {
			// 			return '<span class="label label-danger">離異</span>';
			// 		} else if (value === '2') {
			// 			return '<span class="label label-warning">喪偶</span>';
			// 		} else {
			// 			return '<span class="label label-default">已婚</span>';
			// 		}
			// 	}
			// },
			// { label: '戶籍地址', name: 'homeAddr', width: 100 },
			// { label: '聯絡地址', name: 'contactAddr', width: 100 },
			{ label: '住家電話', name: 'homePhone', width: 100 },
			// { label: '緊急聯絡人', name: 'pressingPerson', width: 100 },
			// { label: '緊急連絡人關係', name: 'pressingRelation', width: 100 },
			{ label: '緊急連絡電話', name: 'pressingPhone', width: 100 },
			// { label: '報到日期', name: 'registerDate', width: 100 },
			// { label: '離職日期', name: 'resignDate', width: 100 },
			// { label: '大頭貼', name: 'empPhoto', width: 100 },
			// { label: '離職證明', name: 'resignPhoto', width: 100 },
			// { label: '剩餘特休時數', name: 'yearHoliday', width: 100 },
			// { label: '底薪', name: 'basicSalary', width: 100 },
			{
				label: '狀態', name: 'status', width: 80, formatter: function (value, options, row) {
					if (value === 0) {
						return '<span class="label label-primary">在職中</span>';
					} else if (value === 1) {
						return '<span class="label label-danger">留職停薪</span>';
					} else {
						return '<span class="label label-default">已離職</span>';
					}
				}
			},


			// { label: '建立時間', name: 'createTime', index: "create_time", width: 80 }
		],
		viewrecords: true,
		height: 385,
		rowNum: 10,
		rowList: [10, 30, 50],
		rownumbers: true,
		rownumWidth: 25,
		autowidth: true,
		multiselect: true,
		pager: "#jqGridPager",
		jsonReader: {
			root: "page.list",
			page: "page.currPage",
			total: "page.totalPage",
			records: "page.totalCount"
		},
		prmNames: {
			page: "page",
			rows: "limit",
			order: "order"
		},
		gridComplete: function () {
			//隱藏grid底部滾動條
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
		}
	});
});

var setting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "parentId",
			rootPId: -1
		},
		key: {
			url: "nourl"
		}
	}
};
var ztree;

var vm = new Vue({
	el: '#rapp',
	data: {
		q: {
			keyword: null
		},
		showList: true,
		title: null,
		roleList: {},
		user: {
			status: 1,
			gender: 0,
			bloodType: 0,
			personType: 0,
			soldierType: 0,
			marriedType: 0,
			roleIdList: [],
			roleName:"",
		}
	},
	methods: {

		query: function () {

			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.roleList = {};
			vm.user = {
				status: 1,
				gender: 0,
				bloodType: 0,
				personType: 0,
				soldierType: 0,
				marriedType: 0,

				roleIdList: []
			};

			//獲取角色信息
			this.getRoleList();

		},
		update: function () {
			var userId = getSelectedRow();
			if (userId == null) {
				return;
			}

			vm.showList = false;
			vm.title = "修改";

			vm.getUser(userId);
			//獲取角色信息
			this.getRoleList();
		},
		del: function () {
			var userIds = getSelectedRows();
			if (userIds == null) {
				return;
			}

			confirm('確定刪除此資料？', function () {
				$.ajax({
					type: "POST",
					url: baseURL + "/sys/user/delete",
					contentType: "application/json",
					data: JSON.stringify(userIds),
					success: function (r) {
						if (r.code == 0) {
							alert('操作成功', function () {
								vm.reload();
							});
						} else {
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function () {
			var url = vm.user.id == null ? "/sys/user/save" : "/sys/user/update";
			if (vm.user.id != null) {
				//修改會員時，不會有建立時間;只有新增會員才會有建立時間
				vm.user.createTime = null;
			}

			var formData = new FormData();

			formData.append('user', JSON.stringify(vm.user)); 

			if (document.getElementById('empPhotoInput').files[0] === undefined) {
				alert('請選擇大頭貼照片');
				return;
			}

			formData.append('empPhoto', document.getElementById('empPhotoInput').files[0]);

			$.ajax({
				type: "POST",
				url: baseURL + url,
				contentType: false,
				processData: false,
				data: formData,
				success: function (r) {
					if (r.code === 0) {
						alert('操作成功', function () {
							vm.reload();
						});
					} else {
						alert(r.msg);
					}
				}
			});
		},
		getUser: function (userId) {
			$.get(baseURL + "/sys/user/info/" + userId, function (r) {
				vm.user = r.user;

				// vm.user.password = null;
			});
		},
		getRoleList: function () {
			$.get(baseURL + "/sys/role/select", function (r) {
				vm.roleList = r.list;

			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
				postData: { 'keyword': vm.q.keyword },
				page: page
			}).trigger("reloadGrid");
		},
		updateSelectedRoles(roleName) {
			vm.user.roleName = roleName;
		  }

	}
});