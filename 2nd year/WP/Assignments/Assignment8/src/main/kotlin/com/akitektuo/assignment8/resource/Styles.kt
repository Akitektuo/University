package com.akitektuo.assignment8.resource

val generalStyle = """
<style>
    body {
        height: 100vh;
        width: 100vw;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        overflow: hidden;
    }

    form.container {
        box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
        padding: 32px;
    }

    h2 {
        margin: 0;
    }

    .form-header {
        margin-bottom: 16px;
    }

    .label-with-input {
        display: flex;
        flex-direction: column;
        margin: 8px 0;
    }

    .medium-input {
        width: 192px;
        margin-top: 4px;
    }

    .form-actions {
        margin-top: 32px;
        display: flex;
        align-items: center;
        justify-content: flex-end;
    }
    
    .error {
        color: red;
    }
</style>
""".trimIndent()