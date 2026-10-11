# The Black Fog

Mod de terror para **Minecraft Java 1.20.1**, usando **Minecraft Forge 47.x** e **Java 17**.

## Conceito
- **De dia:** a criatura observa à distância e evita o confronto.
- **À noite:** fica agressiva e caça jogadores.
- **Lanterna da Névoa:** cada jogador recebe uma vez ao entrar no mundo pela primeira vez. Ao usar, projeta partículas à frente e concede visão noturna breve como efeito provisório.

## Estado atual
Este repositório contém a base do projeto e a primeira implementação de comportamento. É um **protótipo em desenvolvimento**, não uma versão final testada dentro do Minecraft. O feixe atual é visual/funcional como protótipo; iluminação dinâmica real de blocos, aparições específicas em janelas e sustos visuais personalizados ainda precisam de implementação e testes.

## Requisitos
- Java JDK 17
- Minecraft Forge 1.20.1 (47.x)
- Gradle compatível com ForgeGradle 6

## Compilar
Se você tiver o Gradle instalado:
```bash
gradle build
```
O JAR será gerado em `build/libs/`.

Para desenvolvimento, o caminho mais seguro é importar o projeto no IntelliJ IDEA ou Eclipse com Java 17 e Gradle, sincronizar e executar a configuração `runClient`.

## Identificadores
- Mod ID: `theblackfog`
- Entidade: `theblackfog:black_fog`
- Item: `theblackfog:fog_lantern`

## Próximas etapas
1. Testar a compilação com ForgeGradle 6.
2. Adicionar modelo/textura artística própria para a criatura.
3. Melhorar aparições diurnas e sumiço quando observada.
4. Implementar iluminação dinâmica real para a lanterna.
5. Ajustar spawn e balanceamento em jogo.

Este projeto é um protótipo original de fã e não é afiliado à Mojang ou Microsoft.