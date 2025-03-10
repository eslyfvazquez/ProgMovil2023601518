class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { UIPrincipal() }
    }
}

@Composable
fun UIPrincipal() {
    val contexto = LocalContext.current
    var nombre by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Agrega margen a toda la columna
        verticalArrangement = Arrangement.Center // Centra los elementos verticalmente
    ){

    Text(text = "Nombre: ")
    OutlinedTextField(
        value = nombre,
        onValueChange = { nombre = it },
        label = { Text("Introduce tu nombre")},

        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp) // Espacio entre elementos
        )

    Button(
        onClick = {
            Toast.makeText(contexto, "Hola $nombre", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.align(Alignment.CenterHorizontally) // Centra el botón
        ) {
        Text("Saludar!")
    }
    }
}

@Preview(showBackground = true)
@Composable
fun Previsualizacion() {
    UIPrincipal()
    }
