$(function () {
    initResource();
    $("#add_product").click(function () {
        $('.tables').load('product/product-add.html');
    });
    $("#product_list").click(function () {
        $('.tables').load('product/product-list.html');
    });
    var node;
    var setting = {
        callback: {
            onClick: function (event, treeId, treeNode, clickFlag) {
                $("#formDemo").empty();
                var parentname_tree = treeNode.getParentNode();
                var parentname;
                if (parentname_tree) {
                    parentname = parentname_tree.name;
                } else {
                    parentname = "";
                }
                node = treeNode;
            }
        },
    };
    // 初始化页面
    function initResource() {
        getTypeList()//获取类目信息
        bindEvent()
    }
    //获取所有类目信息
    function getTypeList() {
        $.ajax({
            url: DOMIN.MAIN + "/virtualtopup/queryOilGoodsTypeALL",
            type: "GET",
            processData: true,
            cache: false,
            async: true,
            dataType: "json",
            data: {},
            traditional: true, // 使用传统方式序列化
            success: function (data, textStatus) {
                if (data.success) {
                    var datasAll = JSON.parse(data.data.ztreeData)
                    $.fn.zTree.init($("#treeDemo"), setting, datasAll.list)
                } else {
                    $.tip(data.message);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.tip(errorThrown);
            }
        });
    }
    // 绑定事件
    function bindEvent() {
        $('.search-box button').on('click', function () {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var getnode = zTree.getSelectedNodes();
            if (getnode == "") {
                node = "";
            }
            var a = $(this).attr('data-t');
            switch (a) {
                case 'add': add()
                    break;
                case 'xiugai':
                    if (node) {
                        xiugai(node);
                        break;
                    } else {
                        $.tip('请选择你要修改的类目')
                    }
                    break;
            }
        })
    }
    // 添加类目
    function add() {
        getFirst()
    }
    // 修改类目
    var cateId; // 修改类目所需id
    function xiugai($node) {
        cateId = $node.entity.id;
        if ($node) {
            $('.tips-center').html('');
            var str = '<div class="formTable">'
                + '<label>类目名称:</label>'
                + '<input type="text" class="typeName">'
                + '</div>'
                + '<div class="formTable">'
                + ' <label style="vertical-align: top;">备注：</label>'
                + ' <textarea name="beizhu" id="beizhuT" maxlength="150"></textarea>'
                + '</div>'
            $('.tips-warp').show();
            $('.tips-top p').html('添加类目');
            $('.tips-center').html(str);
            $('.typeName').val($node.name)
            $('.ok_btn').attr('data-type', false)
        }
    }
    // 提交
    $(".ok_btn").on('click', function () {
        if ($(this).attr('data-type') == 'true') {
            addCategory();
        } else {
            queryCategory();
        }
    })
    // 修改类目
    function queryCategory() {
        var dataT = {
            id: cateId,
            typeName: $('.typeName').val(),
            remark: $('#beizhuT').val()
        }
        $.ajax({
            url: DOMIN.MAIN + "/virtualtopup/updateOilGoodsType",
            type: "POST",
            processData: true,
            cache: false,
            async: true,
            dataType: "json",
            data: dataT,
            traditional: true, // 使用传统方式序列化
            success: function (data, textStatus) {
                if (data.success) {
                    //   提交成功后刷新页面
                    $('.tips-warp').hide();
                    $.tip('修改类目成功!');
                    setTimeout(function () { getTypeList() }, 500)
                } else {
                    $.tip(data.message);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.tip(errorThrown);
            }
        });
    }
    // 添加类目
    function addCategory() {
        var dataStr = {
            typeName: $('.addCate').val(),
            typeGrade: parseInt($('#category option:selected').attr('data-typeGrade')) + 1,
            parentId: $('#category option:selected').attr('data-parentId'),
            remark: $('#beizhu').val()
        }
        $.ajax({
            url: DOMIN.MAIN + "/virtualtopup/addOilGoodsType",
            type: "PUT",
            processData: true,
            cache: false,
            async: true,
            dataType: "json",
            data: dataStr,
            traditional: true, // 使用传统方式序列化
            success: function (data, textStatus) {
                if (data.success) {
                    //   提交成功后刷新页面
                    $('.tips-warp').hide();
                    $.tip('添加类目成功!');
                    setTimeout(function () { getTypeList() }, 500)
                } else {
                    $.tip(data.message);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.tip(errorThrown);
            }
        });
    }
    // 获取上级类目
    function getFirst() {
        $.ajax({
            url: DOMIN.MAIN + "/virtualtopup/queryOilGoodsTypeName",
            type: "GET",
            processData: true,
            cache: false,
            async: true,
            dataType: "json",
            data: {},
            traditional: true, // 使用传统方式序列化
            success: function (data, textStatus) {
                if (data.success) {
                    $('.tips-center').html('');
                    var str = '<div class="formTable">'
                    str += '<label>新增类目名称:</label>'
                    str += '<input type="text" class="addCate">'
                    str += '</div>'
                    str += '<div class="formTable">'
                    str += '<label>上级类目:</label>'
                    str += '<select id="category">'
                    var short;
                    for (var i = 0; i < data.list.length; i++) {
                        short = data.list[i].oilGoodsType;
                        str += '<option  data-typeGrade=' + short.typeGrade + ' data-parentId=' + short.id + '>' + data.list[i].showName + '</option>'
                    }
                    str += ' </select>'
                    str += '</div>'
                    str += '<div class="formTable">'
                    str += ' <label style="vertical-align: top;">备注：</label>'
                    str += ' <textarea name="beizhu" id="beizhu" maxlength="150"></textarea>'
                    str += '</div>'
                    $('.tips-warp').show();
                    $('.tips-top p').html('添加类目');
                    $('.tips-center').html(str);
                    $('.ok_btn').attr('data-type', true)
                } else {
                    $.tip(data.message);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.tip(errorThrown);
            }
        });
    }
});
