search = function (request, response) {

        if ($("#subject").val().length > 2)

            $.ajax({

                url: "/topics/checkTopic",
                data: {"title": $("#subject").val()},
                dataType: "json",
                success: function (resp) {
                    $("#result").text("");

                    if (resp.result.length > 0) {
                        $("#result_status").removeClass('hidden');
                        $("#result_status").text("There are some topics with similar name:");
                        for (var i = 0; i < resp.result.length; i++) {
                            $("#result").append(
                                "<h3>" +
                                "<a href='/topics/" + resp.result[i].id + "'> " + resp.result[i].title + "</a>" +
                                "</h3>"
                                );
                        }
                    } else {
                        $("#result_status").addClass('hidden');
                    }
                },
                error: function (jqXHR, exception) {

                }
            })
        else{
            $("#result").text("");
            $("#result_status").text("");
            $("#result_status").addClass('hidden');
        }
    }

