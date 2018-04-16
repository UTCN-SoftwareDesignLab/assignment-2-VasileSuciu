package demo.database;

import java.util.*;

import static demo.database.Constants.Rights.*;
import static demo.database.Constants.Roles.*;

public class Constants {

    public static class Schemas {
        public static final String TEST = "test_book";
        public static final String PRODUCTION = "book";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHTS = "rights";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String SALE = "sale";
        public static final String BOOK = "book";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHTS,
                            ROLE_RIGHT, USER_ROLE, SALE, BOOK};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";

        public static final String ADD_BOOK = "create_client";
        public static final String DELETE_BOOK = "delete_client";
        public static final String UPDATE_BOOK = "update_client";
        public static final String SELL_BOOK = "view_client";


        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, ADD_BOOK,
                        DELETE_BOOK, UPDATE_BOOK, SELL_BOOK};
    }

    public static class Genres{
        public static final String DRAMA = "drama";
        public static final String ROMANCE = "romance";
        public static final String SCIENCE_FICTION = "science fction";
        public static final String SCIENCE = "science";
        public static final String KIDS = "kids";
        public static final String ART = "art";

        public static final String[] GENRES = new String[]{DRAMA,
                ROMANCE, SCIENCE_FICTION, SCIENCE, KIDS, ART};
    }

    public static class Reports{
        public static final String CSV = "csv";
        public static final String PDF = "pdf";
    }

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
        for (String role : ROLES) {
            ROLES_RIGHTS.put(role, new ArrayList<>());
        }
        ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(CREATE_USER, DELETE_USER, UPDATE_USER));

        ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(ADD_BOOK, DELETE_BOOK, UPDATE_BOOK, SELL_BOOK));

        return ROLES_RIGHTS;
    }

}
