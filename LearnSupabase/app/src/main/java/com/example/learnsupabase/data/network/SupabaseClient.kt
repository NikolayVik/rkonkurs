package com.example.learnsupabase.data.network


import com.example.learnsupabase.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {

    val client = createSupabaseClient(
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFqZHZ1b2tuaHNqY293YWh0Znp0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDk5NjQ2MDgsImV4cCI6MjAyNTU0MDYwOH0.NkbjhPKZ8tgZYWz1vO_V7v1AKAWfh1T8077j42OWbuM",
        supabaseUrl = "https://qjdvuoknhsjcowahtfzt.supabase.co"
    ){
        install(GoTrue)
        install(Postgrest)
    }
}
