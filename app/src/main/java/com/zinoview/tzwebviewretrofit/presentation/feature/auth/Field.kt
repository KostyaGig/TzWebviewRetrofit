package com.zinoview.tzwebviewretrofit.presentation.feature.auth

interface Field {

    fun isEmpty(fields: List<String>) : Boolean

    fun isNotValid(fields: List<String>) : Boolean

    fun isValidLogin(login: String) : Boolean

    class Base : Field {

        override fun isEmpty(fields: List<String>): Boolean {
            var empty = true
            fields.forEach { field ->
                empty = field.isEmpty()
            }
            return empty
        }

        override fun isNotValid(fields: List<String>) : Boolean {
            val login = fields[0]
            val password = fields[1]
            return (login == MOCK_LOGIN && password == MOCK_PASSWORD).not()
        }

        override fun isValidLogin(login: String): Boolean
            = login == MOCK_LOGIN

        private companion object {

            private const val MOCK_LOGIN = "mock_login"
            private const val MOCK_PASSWORD = "12345"
        }
    }
}