package com.campusfin.service;

import com.campusfin.model.StudentFinancialProfile;
import com.campusfin.model.User;
import com.campusfin.repository.StudentFinancialProfileRepository;
import com.campusfin.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StudentFinancialProfileService {

    private static final String SESSION_PROFILE =
            "studentFinancialProfile";

    private final StudentFinancialProfileRepository repository;
    private final UserRepository userRepository;

    public StudentFinancialProfileService(
            StudentFinancialProfileRepository repository,
            UserRepository userRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
    }


    // ----------------------------------------------------
    // Get profile for logged-in or anonymous user
    // ----------------------------------------------------

    public StudentFinancialProfile getOrCreateProfile(
            HttpSession session) {

        User currentUser = getCurrentUserOrNull();

        /*
         * Logged-in user:
         * load or create persistent database profile.
         */
        if (currentUser != null) {

            StudentFinancialProfile profile =
                    repository
                            .findByUser(currentUser)
                            .orElseGet(() ->
                                    createPersistentProfile(
                                            currentUser
                                    )
                            );

            session.setAttribute(
                    SESSION_PROFILE,
                    profile
            );

            return profile;
        }


        /*
         * Anonymous user:
         * keep profile only in browser session.
         */
        StudentFinancialProfile sessionProfile =
                (StudentFinancialProfile)
                        session.getAttribute(
                                SESSION_PROFILE
                        );

        if (sessionProfile == null) {

            sessionProfile =
                    new StudentFinancialProfile();

            session.setAttribute(
                    SESSION_PROFILE,
                    sessionProfile
            );
        }

        return sessionProfile;
    }


    // ----------------------------------------------------
    // Save Profile
    // ----------------------------------------------------

    public StudentFinancialProfile saveProfile(
            StudentFinancialProfile profile,
            HttpSession session) {

        User currentUser = getCurrentUserOrNull();

        /*
         * Logged-in user:
         * save permanently in database.
         */
        if (currentUser != null) {

            profile.setUser(currentUser);

            StudentFinancialProfile savedProfile =
                    repository.save(profile);

            session.setAttribute(
                    SESSION_PROFILE,
                    savedProfile
            );

            return savedProfile;
        }


        /*
         * Anonymous user:
         * keep only in browser session.
         */
        profile.setUser(null);

        session.setAttribute(
                SESSION_PROFILE,
                profile
        );

        return profile;
    }


    // ----------------------------------------------------
    // Get Saved Profile
    // ----------------------------------------------------

    public StudentFinancialProfile getSavedProfile(
            HttpSession session) {

        User currentUser = getCurrentUserOrNull();

        /*
         * Logged-in user:
         * retrieve persistent profile.
         */
        if (currentUser != null) {

            StudentFinancialProfile profile =
                    repository
                            .findByUser(currentUser)
                            .orElse(null);

            if (profile != null) {

                session.setAttribute(
                        SESSION_PROFILE,
                        profile
                );
            }

            return profile;
        }


        /*
         * Anonymous user:
         * retrieve temporary session profile.
         */
        return (StudentFinancialProfile)
                session.getAttribute(
                        SESSION_PROFILE
                );
    }


    // ----------------------------------------------------
    // Create Persistent Profile
    // ----------------------------------------------------

    private StudentFinancialProfile createPersistentProfile(
            User currentUser) {

        StudentFinancialProfile profile =
                new StudentFinancialProfile();

        profile.setUser(currentUser);

        return repository.save(profile);
    }


    // ----------------------------------------------------
    // Get Current Logged-In User If Available
    // ----------------------------------------------------

    private User getCurrentUserOrNull() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(
                        authentication.getPrincipal()
                )) {

            return null;
        }

        String email =
                authentication
                        .getName()
                        .trim()
                        .toLowerCase();

        return userRepository
                .findByEmail(email)
                .orElse(null);
    }
}