from game import Game

def test_stars():
    game = Game()
    game.place_stars()

    count = 0
    for i in range(1, 9):
        for j in range(1, 9):
            if game.grid[i][j].type == 1:
                if game.grid[i - 1][j].type == 1 or game.grid[i + 1][j].type == 1 or game.grid[i][j - 1].type == 1 or game.grid[i][j + 1].type == 1 or game.grid[i - 1][j - 1].type == 1 or game.grid[i - 1][j + 1].type == 1 or game.grid[i + 1][j - 1].type == 1 or game.grid[i + 1][j + 1].type == 1:
                    assert False
                count += 1
    assert count == 10
