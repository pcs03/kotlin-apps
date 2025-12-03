package nl.pcstet.core.network.data

object TestResponses {
    val valid = """
        {
            "status": "ok",
            "method": "POST"
        }
    """.trimIndent()

    val invalid = """
        {
            "method": "POST"
        }
    """.trimIndent()
}