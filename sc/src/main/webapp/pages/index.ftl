<html>
<head>
    <script src="http://www.tbc.com/xfs/js/jquery.min.js"></script>
    <script src="http://malsup.github.io/jquery.form.js"></script>
    <script>
        $(function () {
            $("#id").click(function () {
                $.post("http://localhost:8080/elf/file/testJson", function (json) {
                    alert(json.responseFormat);
                    alert(json);
                    console.log(json);
                });
            });

            $("#upload").click(function () {
                $("#fileForm").ajaxSubmit({
                    url: '/elf/file/uploadFile',
                    type: 'post',
                    success: function (data) {
                        console.log(data);
                    }
                });
            });
        });
    </script>
</head>
<body>

<#--
<a id="id" href="javascript:void(0)" style="display:block;width: 200px;height: 200px;color: red">a</a>
-->

<form id="fileForm" action="/elf/file/uploadFile" method="post" enctype="multipart/form-data">
    file1:<input type="file" name="file1" style="margin-left: 200px;margin-bottom: 100px"/><br>
<#--
    file2:<input type="file" name="file2" style="margin-left: 200px;margin-bottom: 100px"/><br>
-->
    <input type="hidden" name="module" value="uc"/><br>
    <input type="button" value="upload" style="margin-left: 200px;margin-bottom: 100px" id="upload"/>

</form>
</body>
</html>

