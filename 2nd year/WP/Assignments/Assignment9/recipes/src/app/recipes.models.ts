export interface Type {
    id: number;
    name: string;
    image: string;
}

export interface Recipe {
    id?: number;
    userId: number;
    typeId: number;
    title: string;
    description: string;
}

export interface RecipeWithData extends Recipe {
    user: string;
    type: string;
}

export const EMPTY_RECIPE: Recipe = {
    userId: 1,
    typeId: 1,
    title: "",
    description: "",
}