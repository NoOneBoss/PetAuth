package me.nooneboss

import io.ktor.server.application.*
import io.ktor.server.html.*
import kotlinx.html.*

object LoginPage {
    suspend fun renderLoginPage(call: ApplicationCall) {
        call.respondHtml {
            head {
                title("Успешная отметка")
                style {
                    +"""
                    body {
                        font-family: Arial, sans-serif;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        margin: 0;
                        background: linear-gradient(135deg, #71b7e6, #9b59b6);
                    }
                    .container {
                        text-align: center;
                        background: white;
                        padding: 50px;
                        border-radius: 10px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    }
                    h1 {
                        color: #4CAF50;
                        font-size: 3em;
                    }
                    p {
                        color: #333;
                        font-size: 2em;
                    }
                """.trimIndent()
                }
            }
            body {
                div("container") {
                    h1 { +"Успешная отметка!" }
                    p { +"Ваше присутствие успешно зафиксировано." }
                }
            }
        }
    }

    suspend fun renderAlreadyLoginPage(call: ApplicationCall) {
        call.respondHtml {
            head {
                title("Вы уже отмечали присутсвие!")
                style {
                    +"""
                    body {
                        font-family: Arial, sans-serif;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        margin: 0;
                        background: linear-gradient(135deg, #71b7e6, #9b59b6);
                    }
                    .container {
                        text-align: center;
                        background: white;
                        padding: 50px;
                        border-radius: 10px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    }
                    h1 {
                        color: #ff0033;
                        font-size: 3em;
                    }
                    p {
                        color: #333;
                        font-size: 2em;
                    }
                """.trimIndent()
                }
            }
            body {
                div("container") {
                    h1 { +"Вы уже отмечали своё присутсвие!" }
                    p { +"Попробуйте позже." }
                }
            }
        }
    }
}