package nl.pcstet.core.network.data

object LoginResponses {
    val valid = """
        {
            "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJlbWlseXMiLCJlbWFpbCI6ImVtaWx5LmpvaG5zb25AeC5kdW1teWpzb24uY29tIiwiZmlyc3ROYW1lIjoiRW1pbHkiLCJsYXN0TmFtZSI6IkpvaG5zb24iLCJnZW5kZXIiOiJmZW1hbGUiLCJpbWFnZSI6Imh0dHBzOi8vZHVtbXlqc29uLmNvbS9pY29uL2VtaWx5cy8xMjgiLCJpYXQiOjE3NjQwMDU0MjIsImV4cCI6MTc2NDAwOTAyMn0.Dd2q0LwMtFt6Z_Yo9G98NKEgksqZWiyRlU3KrRcDajw",
            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJlbWlseXMiLCJlbWFpbCI6ImVtaWx5LmpvaG5zb25AeC5kdW1teWpzb24uY29tIiwiZmlyc3ROYW1lIjoiRW1pbHkiLCJsYXN0TmFtZSI6IkpvaG5zb24iLCJnZW5kZXIiOiJmZW1hbGUiLCJpbWFnZSI6Imh0dHBzOi8vZHVtbXlqc29uLmNvbS9pY29uL2VtaWx5cy8xMjgiLCJpYXQiOjE3NjQwMDU0MjIsImV4cCI6MTc2NjU5NzQyMn0.FFktQPkHFEcOcgdauIbsJjdrjkY0gaxdv5lA1tvdkIw",
            "id": 1,
            "username": "emilys",
            "email": "emily.johnson@x.dummyjson.com",
            "firstName": "Emily",
            "lastName": "Johnson",
            "gender": "female",
            "image": "https://dummyjson.com/icon/emilys/128"
        } 
    """.trimIndent()

    val invalid = """
        {
            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJlbWlseXMiLCJlbWFpbCI6ImVtaWx5LmpvaG5zb25AeC5kdW1teWpzb24uY29tIiwiZmlyc3ROYW1lIjoiRW1pbHkiLCJsYXN0TmFtZSI6IkpvaG5zb24iLCJnZW5kZXIiOiJmZW1hbGUiLCJpbWFnZSI6Imh0dHBzOi8vZHVtbXlqc29uLmNvbS9pY29uL2VtaWx5cy8xMjgiLCJpYXQiOjE3NjQwMDU0MjIsImV4cCI6MTc2NjU5NzQyMn0.FFktQPkHFEcOcgdauIbsJjdrjkY0gaxdv5lA1tvdkIw",
            "id": 1,
            "username": "emilys",
            "email": "emily.johnson@x.dummyjson.com",
            "firstName": "Emily",
            "lastName": "Johnson",
            "gender": "female",
            "image": "https://dummyjson.com/icon/emilys/128"
        }
    """.trimIndent()
}