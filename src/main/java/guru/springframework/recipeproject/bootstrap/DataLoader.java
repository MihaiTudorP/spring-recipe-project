package guru.springframework.recipeproject.bootstrap;

import guru.springframework.recipeproject.domain.*;
import guru.springframework.recipeproject.repositories.CategoryRepository;
import guru.springframework.recipeproject.repositories.RecipeRepository;
import guru.springframework.recipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.Set;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        final UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();
        final UnitOfMeasure piece = unitOfMeasureRepository.findByDescription("Piece").get();
        final UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();
        final UnitOfMeasure dash = unitOfMeasureRepository.findByDescription("Dash").get();
        final UnitOfMeasure some = unitOfMeasureRepository.findByDescription("Some").get();


        Category category = categoryRepository.findByDescription("Mexican").get();
        Recipe perfectGuacamole = new Recipe();
        Set<Category> categories = Set.of(category);
        perfectGuacamole.setCategories(categories);
        Ingredient avocado = new Ingredient();
        avocado.setUnitOfMeasure(piece);
        avocado.setAmount(BigDecimal.valueOf(2));
        avocado.setDescription("Avocado");
        Ingredient salt = new Ingredient();
        salt.setDescription("Salt");
        salt.setAmount(BigDecimal.valueOf(0.25));
        salt.setUnitOfMeasure(teaspoon);
        Ingredient lJuice = new Ingredient();
        lJuice.setDescription("Fresh lime juice or lemon juice");
        lJuice.setUnitOfMeasure(tablespoon);
        lJuice.setAmount(BigDecimal.valueOf(1));
        Ingredient onion = new Ingredient();
        onion.setDescription("Minced red onion or thinly sliced green onion");
        onion.setAmount(BigDecimal.valueOf(2));
        onion.setUnitOfMeasure(tablespoon);
        Ingredient serranoChile = new Ingredient();
        serranoChile.setDescription("Serrano chiles, stems and seeds removed, minced");
        serranoChile.setAmount(BigDecimal.valueOf(2));
        serranoChile.setUnitOfMeasure(piece);
        Ingredient cilantro = new Ingredient();
        cilantro.setDescription("Cilantro (leaves and tender stems), finely chopped");
        cilantro.setAmount(BigDecimal.valueOf(2));
        cilantro.setUnitOfMeasure(tablespoon);
        Ingredient blackPepper = new Ingredient();
        blackPepper.setDescription("Freshly grated black pepper");
        blackPepper.setAmount(BigDecimal.valueOf(1));
        blackPepper.setUnitOfMeasure(dash);
        Ingredient tomato = new Ingredient();
        tomato.setDescription("Ripe tomato, seeds and pulp removed, chopped");
        tomato.setAmount(BigDecimal.valueOf(0.5));
        tomato.setUnitOfMeasure(piece);
        Ingredient radishesOrjicama = new Ingredient();
        radishesOrjicama.setDescription("Red radishes or jicama, to garnish");
        radishesOrjicama.setAmount(BigDecimal.valueOf(1));
        radishesOrjicama.setUnitOfMeasure(some);
        Ingredient tortillaChips = new Ingredient();
        tortillaChips.setDescription("Tortilla chips, to serve");
        tortillaChips.setAmount(BigDecimal.valueOf(1));
        tortillaChips.setUnitOfMeasure(some);
        Set<Ingredient> ingredients = Set.of(avocado, salt, lJuice, onion, serranoChile, cilantro, blackPepper, tomato, radishesOrjicama, tortillaChips);
        ingredients.forEach(ingredient -> {ingredient.setRecipe(perfectGuacamole);});
        Notes notes = new Notes();
        notes.setRecipe(perfectGuacamole);
        notes.setRecipeNotes("Guacamole is best eaten right after it’s made. Like apples, avocados start to oxidize and turn brown once they’ve been cut. That said, the acid in the lime juice you add to guacamole can help slow down that process, and if you store the guacamole properly, you can easily make it a few hours ahead if you are preparing for a party.\n" +
                "The trick to keeping guacamole green is to make sure air doesn’t touch it! Transfer it to a container, cover with plastic wrap, and press down on the plastic wrap to squeeze out any air pockets. Make sure any exposed surface of the guacamole is touching the plastic wrap, not air. This will keep the amount of browning to a minimum.\n" +
                "You can store the guacamole in the fridge this way for up to three days.\n" +
                "If you leave the guacamole exposed to air, it will start to brown and discolor. That browning isn’t very appetizing, but the guacamole is still good. You can either scrape off the brown parts and discard, or stir them into the rest of the guacamole.");

        perfectGuacamole.setDescription("Perfect Guacamole");
        perfectGuacamole.setCategories(Set.of(category));
        perfectGuacamole.setServings(4);
        perfectGuacamole.setDifficulty(Difficulty.MODERATE);
        perfectGuacamole.setNotes(notes);
        perfectGuacamole.setDirections("Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n");
        perfectGuacamole.setIngredients(ingredients);
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setSource("Simply recipes");
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        recipeRepository.save(perfectGuacamole);

        Recipe sauteedSpinach = new Recipe();
        Ingredient spinach = new Ingredient();
        spinach.setDescription("Spinach");
        spinach.setAmount(BigDecimal.valueOf(2));
        spinach.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Bunch").get());
        Ingredient oliveOil = new Ingredient();
        oliveOil.setAmount(BigDecimal.valueOf(1));
        oliveOil.setDescription("Extra virgin olive oil");
        oliveOil.setUnitOfMeasure(some);
        Ingredient garlic = new Ingredient();
        garlic.setDescription("Garlic, sliced");
        garlic.setAmount(BigDecimal.valueOf(3));
        garlic.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Clove").get());
        Ingredient saltToTaste = new Ingredient();
        saltToTaste.setDescription("Salt to taste");
        saltToTaste.setAmount(BigDecimal.valueOf(1));
        saltToTaste.setUnitOfMeasure(some);

        ingredients = Set.of(spinach, oliveOil, garlic, saltToTaste);
        ingredients.forEach(ingredient -> {ingredient.setRecipe(sauteedSpinach);});
        category = categoryRepository.findByDescription("American").get();
        categories = Set.of(category);
        notes = new Notes();
        notes.setRecipe(sauteedSpinach);
        notes.setRecipeNotes("My father prepares spinach this way at least once or twice a week, usually made with fresh spinach from the farmer’s market. According to dad he overcooked it for years, until he learned that you shouldn’t cook spinach beyond the point that it just wilts.\n" +
                "Spinach releases a lot of water as it cooks, so my father’s trick is to drain and dry the spinach leaves as well as you can, using a salad spinner if need be, before cooking them.\n" +
                "Then sauté some garlic in olive oil in a large wide pan, and add the cleaned, drained, and dried spinach leaves to the pan. Pack the pan with spinach, cover and cook for only a minute or two tops.");

        sauteedSpinach.setIngredients(ingredients);
        sauteedSpinach.setDifficulty(Difficulty.EASY);
        sauteedSpinach.setServings(4);
        sauteedSpinach.setPrepTime(5);
        sauteedSpinach.setCookTime(5);
        sauteedSpinach.setSource("Simply recipes");
        sauteedSpinach.setUrl("https://www.simplyrecipes.com/recipes/spinach/");
        sauteedSpinach.setCategories(categories);
        sauteedSpinach.setDescription("Sautéed Spinach");
        sauteedSpinach.setNotes(notes);
        sauteedSpinach.setDirections(" Clean and prep the spinach: Cut off the thick stems of the spinach and discard. Clean the spinach by filling up your sink with water and soaking the spinach to loosen any sand or dirt. Drain the spinach and then repeat soaking and draining. Put the spinach in a salad spinner to remove any excess moisture.\n" +
                "Sauté garlic: Heat 2 Tbsp olive oil in a large skillet on medium high heat. Add the garlic and sauté for about 1 minute, until the garlic is just beginning to brown.\n" +
                "Add spinach to pan: Add the spinach to the pan, packing it down a bit if you need to with your hand. Use a couple spatulas to lift the spinach and turn it over in the pan so that you coat more of it with the olive oil and garlic. Do this a couple of times. Cover the pan and cook for 1 minute. Uncover and turn the spinach over again. Cover the pan and cook for an additional minute.\n" +
                "Remove from pan and drain excess liquid: After 2 minutes of covered cooking the spinach should be completely wilted. Remove from heat.\n" +
                "Drain any excess liquid from the pan. Add a little more olive oil, sprinkle with salt to taste. Serve immediately.\n");
        recipeRepository.save(sauteedSpinach);
    }

    private byte[] getImage(String pathname) {
        byte[] image;
        FileInputStream fileInputStream = null;
        File inputFile;
        try{
            inputFile = new File(pathname);
            fileInputStream = new FileInputStream(inputFile);
            image = new byte[(int) inputFile.length()];
            fileInputStream.read(image);
        } catch (FileNotFoundException e){
            System.out.println("File not found!");
            image = null;
        } catch (IOException e) {
            e.printStackTrace();
            image = null;
        } finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return image;
    }
}
