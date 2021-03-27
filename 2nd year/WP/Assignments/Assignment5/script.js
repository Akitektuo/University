$(".open-modal").click(() => {
    $(".overlay").css("display", "flex");
});

$(".close-modal").click(() => {
    $(".overlay").hide();

    const content = $(".dialog > div").toArray()
        .map(element => {
            const input = $(element).find("input");
            const inputValue = input.val();
            
            input.val("");

            return `<b>${$(element).find("h4").html()}:</b>\t${inputValue}`;
        })
        .join("<br>");

    $(".result").html(content);
});