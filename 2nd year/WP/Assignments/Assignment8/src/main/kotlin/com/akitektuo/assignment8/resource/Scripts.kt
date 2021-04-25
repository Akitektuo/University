package com.akitektuo.assignment8.resource

val generalScript = """
<script>
    function navigateTo(url) {
        location.href = url;
    }
</script>
""".trimIndent()

fun reloadAfter(milliseconds: Long) = """
<script>
    window.addEventListener("load", () =>
        setTimeout(() => location.reload(), $milliseconds));
</script>
""".trimIndent()