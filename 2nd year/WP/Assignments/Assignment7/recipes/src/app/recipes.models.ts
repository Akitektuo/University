export interface Type {
    id: number;
    name: string;
    image: string;
}

export interface Recipe {
    id?: number;
    typeId: number;
    title: string;
    author: string;
    description: string;
}

export interface RecipeWithCategory extends Recipe {
    typeName: string;
}

export const EMPTY_RECIPE: Recipe = {
    typeId: 1,
    title: "",
    author: "",
    description: "",
}