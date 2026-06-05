import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Custom scaffold wrapper for consistent layout across screens.
 *
 * @param modifier Optional [Modifier] to apply to the scaffold.
 * @param topAppBar Optional top app bar composable.
 * @param content Main content composable with padding provided by Scaffold.
 */
@Composable
fun PokemonAppScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topAppBar,
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        // Pass scaffold padding to the content
        content(padding)
    }
}
