import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismogo_2.Adaptadores.AnunciosAdapter
import com.example.appturismogo_2.Fragmentos.Anuncio
import com.example.appturismogo_2.R

class FragmentChat : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var anunciosAdapter: AnunciosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        // Datos harcodeados (simulación de datos)
        val listaAnuncios = listOf(
            Anuncio("Juan Pérez", "+123456789", "12345678A", "Tour chachapoyas", "01/07/2024", 4.5),
            Anuncio("jesus López", "+987654321", "87654321B", "Tour selva", "02/03/2024", 9.2) ,
            Anuncio("miguel López", "+987654321", "87654321B", "Tour chavin", "04/04/2024", 5.2) ,
            Anuncio("irene López", "+987654321", "87654321B", "Tour paracas", "01/04/2024", 6.2)

            // Agrega más anuncios según sea necesario
        )

        anunciosAdapter = AnunciosAdapter(requireContext(), listaAnuncios)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = anunciosAdapter

        return view
    }
}
